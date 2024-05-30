package com.geolocation.ip.service;

import com.geolocation.ip.model.IPInfo;
import com.geolocation.ip.model.IPInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IPInfoService {
    private final IPInfoRepository ipInfoRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = "f659ac59d50944289cb1b551fd15230e";
    private final String openExchangeRatesApiKey = "5499ba681d77cc7443b30740";
    private final String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + apiKey + "&ip=";
    private final String openExchangeRatesUrl = "https://open.er-api.com/v6/latest/USD?apikey=" + openExchangeRatesApiKey;

    public IPInfo getIPInfo(String ip) {
        String url = apiUrl + ip;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject responseData = new JSONObject(response.getBody());

        ResponseEntity<String> responseCurrency = restTemplate.getForEntity(openExchangeRatesUrl, String.class);
        JSONObject responseDataCurrency = new JSONObject(responseCurrency.getBody());

        String countryName = responseData.getString("country_name");
        String isoCode = responseData.getString("country_code2");
        String languages = responseData.getString("languages");
        String timezones = responseData.getJSONObject("time_zone").getString("current_time");
        double latitude = responseData.getDouble("latitude");
        double longitude = responseData.getDouble("longitude");
        double distance = calculateDistance(latitude, longitude, -34.0, -64.0);
        String currencyCode = getCurrency(responseData.getJSONObject("currency"));
        double exchangeRate = responseDataCurrency.getJSONObject("rates").getFloat(responseData.getJSONObject("currency").getString("code"));

        IPInfo ipInfo = new IPInfo();
        ipInfo.setIp(ip);
        ipInfo.setCountry(countryName);
        ipInfo.setIsoCode(isoCode);
        ipInfo.setLanguages(languages);
        ipInfo.setTimezones(timezones);
        ipInfo.setDistance(distance);
        ipInfo.setCurrency(currencyCode);
        ipInfo.setExchangeRate(exchangeRate);
        ipInfoRepository.save(ipInfo);

        return ipInfo;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public List<IPInfo> getAllPInfos() {
        return ipInfoRepository.findAll();
    }

    public String getCurrency(JSONObject currency) {
        return "code: " + currency.getString("code") + " name: " + currency.getString("name") + " symbol: " + currency.getString("symbol");
    }
}
