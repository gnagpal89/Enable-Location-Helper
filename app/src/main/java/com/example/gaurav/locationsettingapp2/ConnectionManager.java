package com.example.gaurav.locationsettingapp2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ConnectionManager {
    private static final String LOG_TAG = "goplus_con";
    //public static final String URL_MAIN = "http://driver.goplus.in/";
    //public static final String URL_MAIN = "http://goplus.in/";"http://104.155.226.206/
    //public static final String URL_MAIN = BuildConfig.URL_ENDPOINT_DS;
    //public static final String URL_BASE = URL_MAIN + "shuttl/";
//    public static final String URL_BASE_PAYMENTS = "http://payments.goplus.in:8080/driver/";
//    private static final String URL_PUBLISH_GPS = URL_BASE + "publishGps";
//    private static final String URL_SEND_DEVICE_STATE = URL_BASE + "sendDeviceStatus";
//    private static final String URL_POST_DEVICES_FOUND = URL_BASE + "devicesFound";
//    private static final String URL_GET_REPORT_DETAILS = URL_BASE + "getReportDetails";
//    private static final String URL_GET_REPORT_INFORMATION = URL_BASE + "getRouteInformation";
//    private static final String URL_SEND_GPS_LOCATIONS = URL_BASE + "batchPublishGps";
//    private static final String URL_SEND_API_LOGS = URL_BASE + "storeDriverApiLogs";
//    private static final String URL_REACH_BY_CALL_OF_DRIVER = URL_BASE + "reachByCallOfDriver";
//    private static final String URL_APP_CONFIG = URL_BASE + "getAppConfig";
//    private static final String URL_GET_BOOKINGS_FOR_TRIP = URL_BASE + "getBookingsForTrip";
//    private static final String URL_BOARDING_STATUS_BY_DRIVER = URL_BASE + "boardingStatusByDriver";
//    private static final String URL_ALERT_SERVICE = URL_BASE + "alertService";
//    private static final String URL_GET_DRIVER_PERSONAL_DETAILS = URL_BASE + "getDriverPersonalDetails";
//    private static final String URL_DRIVER_LOGOUT = URL_BASE + "driverLogOut";
//    private static final String URL_GET_PAYMENT_DETAILS = URL_BASE_PAYMENTS + "getLastDayPaymentDetails";
//    private static final String URL_VERIFY_DRIVER_DEVICE_SIM = URL_BASE + "verifyDriverDeviceSim";
//    private static final String URL_GET_DRIVER_VEHICLE_DETAILS = URL_BASE + "getDriverVehiclesDetails";
//    private static final String URL_DRIVER_CONFIRMATION_CALL = URL_BASE + "driverConfirmationCall";
//    private static final String URL_STORE_DRIVER_START_TRIP_TIME = URL_BASE + "storeDriverStartTripTime";
//    public static final String URL_STORE_DRIVER_END_TRIP_TIME = URL_BASE + "storeDriverEndTripTime";
//    public static final String URL_VERIFY_NUMBER_BY_OTP = URL_BASE + "verifyNumberByOtp";
//    private static final String URL_MARK_OFFLINE_BOARDING = URL_BASE + "markOfflineBoardingByChirp";

    //private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    private static OkHttpClient getOkHttpClient() {
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        // connection timeout
//        client.connectTimeout(HttpServiceGenerator.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
//        // response|socket timeout
//        client.readTimeout(HttpServiceGenerator.RESPONSE_TIMEOUT, TimeUnit.SECONDS);
//        client.interceptors().add(logging);
//        return client.build();
//    }
//
//
//    public static String makePostHTTPRequest(final Context context, String url, String[] key, String[] value, Boolean logApi) {
//
//        FormBody.Builder builder = new FormBody.Builder();
//        int version = 0;
//        PackageInfo pInfo = null;
//        try {
//            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            version = pInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            //TODO Redundant use of "DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID)"
//            String language = DAO.getHelper(context).getSessionVar(Constants.LANGUAGE);
//            if (DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID) != null)
//                //TODO add the keys to Constants
//                builder.add(Constants.MIXPANEL_DRIVER_ID, DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID));
//            builder.add(Constants.HEADER_APP_VERSION, String.valueOf(version));
//            builder.add(Constants.HEADER_LANGUAGE, language != null ? language : "en");
//            builder.add(Constants.HEADER_SESSION_ID, DAO.getHelper(context).getSessionID());
//
//            if (key != null && value != null) {
//                for (int i = 0; i < key.length; i++) {
//                    if (key[i] != null && value[i] != null)
//                        builder.add(key[i], value[i]);
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        RequestBody body = builder.build();
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url)
//                .addHeader(Constants.HEADER_SESSION_ID, DAO.getHelper(context).getSessionID())
//                .addHeader(Constants.HEADER_DRIVER_ID,
//                        (DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID) != null)
//                                ? DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID) : "")
//                .addHeader(Constants.HEADER_VEHICLE_ID,
//                        (DAO.getHelper(context).getSessionVar(Constants.VEHICLE_ID)) != null
//                                ? DAO.getHelper(context).getSessionVar(Constants.VEHICLE_ID) : "")
//                .addHeader(Constants.HEADER_DEVICE_TIME, String.valueOf(System.currentTimeMillis() / 1000L))
//                .addHeader(Constants.HEADER_DEVICE_LATITUDE, LocationNetworkCalls.getInstance().getLocationLat()>0?String.valueOf(
//                        LocationNetworkCalls.getInstance().getLocationLat()):"")
//                .addHeader(Constants.HEADER_DEVICE_LONGITUDE, LocationNetworkCalls.getInstance().getLocationLng()>0?String.valueOf(
//                        LocationNetworkCalls.getInstance().getLocationLng()):"")
//                .post(body).build();
//        //.addHeader("sessionId",DAO.getHelper(context).getSessionID())
//
//
//        String result;
//
//        try {
//            okhttp3.Response response = getOkHttpClient().newCall(request).execute();
//            if (response.code() == Constants.USER_SESSION_INVALID_CODE) {
//                EventBus.getDefault().postSticky(new AutoLogoutEvent());
//            }
//            result = response.body().string();
//        } catch (Exception e) {
//
//            if (e instanceof SocketTimeoutException) {
//                Log.e(LOG_TAG, "timeout");
//            }
//
//            result = "";
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    public static String makeHTTPRequest(Context context, String url, String[] key, String[] value) {
//
//        if (key != null && value != null) {
//
//            for (int i = 0; i < key.length; i++) {
//                if (i == 0) {
//                    url += "?" + key[i] + "=" + value[i];
//                } else {
//                    url += "&" + key[i] + "=" + value[i];
//                }
//            }
//        }
//
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url).addHeader(Constants.HEADER_SESSION_ID, DAO.getHelper(context).getSessionID()).build();
//
//
//        String result;
//        try {
//            okhttp3.Response response = getOkHttpClient().newCall(request).execute();
//            result = response.body().string();
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String makeGetHTTPRequest(final Context context, String url) {
//
//        String result = "";
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url).build();
//        try {
//            okhttp3.Response response = getOkHttpClient().newCall(request).execute();
//            result = response.body().string();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static String makeGetHTTPRequest(Context context, String url, String[] key, String[] value) {
//
//        StringBuilder url1 = new StringBuilder();
//        url1.append(url);
//        int version = 0;
//        PackageInfo pInfo = null;
//        try {
//            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            version = pInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        if (key != null && value != null) {
//
//            for (int i = 0; i < key.length; i++) {
//                try {
//
//                    if (i == 0) {
//                        url1.append("?");
//                    } else {
//                        url1.append("&");
//                    }
//
//                    url1.append(key[i])
//                            .append("=")
//                            .append(URLEncoder.encode(value[i], "utf8"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if (url1.toString().contains("?")) {
//            url1.append("&");
//        } else {
//            url1.append("?");
//        }
//
//        url1.append("driver_id=")
//                .append(DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID))
//                .append("&appVersion=")
//                .append(String.valueOf(version))
//                .append("&language=")
//                .append(DAO.getHelper(context).getSessionVar(Constants.LANGUAGE));
//
//
//        Log.d(LOG_TAG, "makeHTTPRequest url : " + url1.toString());
//        return makeGetHTTPRequest(context, url1.toString());
//    }
//
//    public static String publishDriverGps(Context context, Location location) {
//        String[] key = {"lat", "lng", "driverId", "provider", "accuracy"};
//        String[] value = {location.getLatitude() + "", location.getLongitude() + "",
//                DAO.getHelper(context).getSessionVar(Constants.DRIVER_ID), location.getProvider(), location.getAccuracy() + ""};
//        return makePostHTTPRequest(context, URL_PUBLISH_GPS, key, value, false);
//    }
//
//
//    public static void sendDeviceState(final Context context, final int isBluetoothOn, final int isDiscoverModeOn, final int isInterntConnected, final int isLocationServiceRunning, final String driverid) {
//        Utils.runOnNewThread(new Runnable() {
//            @Override
//            public void run() {
//                String[] key = {"bluetooth", "wifii", "internet", "location", "driverid"};
//                String[] value = {isBluetoothOn + "", isDiscoverModeOn + "", isInterntConnected + "", isLocationServiceRunning + "", driverid + ""};
//                makePostHTTPRequest(context, URL_SEND_DEVICE_STATE, key, value, false);
//            }
//        });
//
//    }
//
//
//    public static void sendCustomerDevicesFound(Context context, ArrayList<Boarding> devices) {
//        String[] key = {"devices_found", "timestamp"};
//        StringBuilder sb = new StringBuilder();
//        StringBuilder sb1 = new StringBuilder();
//        for (int i = 0; i < devices.size(); i++) {
//            if (i < devices.size() - 1) {
//                sb.append(devices.get(i).deviceId + ",");
//                sb1.append(devices.get(i).timeStamp + ",");
//            } else {
//                sb.append(devices.get(i).deviceId);
//                sb1.append(devices.get(i).timeStamp);
//            }
//        }
//        String[] val = {sb.toString(), sb1.toString()};
//        makePostHTTPRequest(context, URL_POST_DEVICES_FOUND, key, val, false);
//    }
//
//
//    public static String getReportDetails(Context context, String driverID) {
//        String[] key = {"driverID"};
//        String[] value = {driverID};
//        return makePostHTTPRequest(context, URL_GET_REPORT_DETAILS, key, value, true);
//
//    }
//
//    public static String getRouteInformation(Context context, String driverID) {
//        String[] key = {"driverID"};
//        String[] value = {driverID};
//        return makePostHTTPRequest(context, URL_GET_REPORT_INFORMATION, key, value, true);
//    }
//
//
//    public static final String alertServiceCall(Context context, Integer currentStage, String routeId, String tripId, Long sessionStartTime, Long sessionEndTime, Float batteryPercentage, Boolean isCharging, String networkType, Float temperature, String airplaneOn, String switchOff, String switchOn, String gpsOff, String gpsOn, String vehicleSessionId) {
//        String[] key = {"stage", "routeId", "tripId", "sessionStartTime", "sessionEndTime", "batteryPercentage", "isCharging", "networkType", "temperature", "airplaneOnTime", "switchOffTime", "switchOnTime", "gpsOffTime", "gpsOnTime", "vehicleSessionId"};
//        String[] value = {currentStage + "", routeId, tripId, sessionStartTime + "", sessionEndTime + "", batteryPercentage + "", isCharging + "", networkType, temperature + "", airplaneOn, switchOff, switchOn, gpsOff, gpsOn, vehicleSessionId};
//        return makePostHTTPRequest(context, URL_ALERT_SERVICE, key, value, false);
//    }
//
//    public static final String sendGpsLocations(Context context, JSONObject jsonObject, long deviceTime) {
//
//        String[] key = {"json", "deviceTime"};
//        String[] value = {jsonObject.toString(), deviceTime + ""};
//        return makePostHTTPRequest(context, URL_SEND_GPS_LOCATIONS, key, value, false);
//    }
//
//
//    public static String sendApiLogsToServer(Context mContext, String driverId, JSONObject apiLogsJsonObject) {
//        String[] key = {"driverId", "apiLogs"};
//        String[] value = {driverId, apiLogsJsonObject.toString()};
//        return makePostHTTPRequest(mContext, URL_SEND_API_LOGS, key, value, false);
//    }
//
//    public static String reachByCallOfDriver(Context context, String driverID) {
//        String[] key = {"driverID"};
//        String[] value = {driverID};
//        return makePostHTTPRequest(context, URL_REACH_BY_CALL_OF_DRIVER, key, value, true);
//    }
//
//    public static String getAppConfig(final Context context) {
//        String[] key = {};
//        String[] value = {};
//        return makePostHTTPRequest(context, URL_APP_CONFIG, key, value, true);
//    }
//
//    public static String fetchBookingsList(Context context, String tripId, String routeId) {
//        String[] key = {"tripId", "routeId"};
//        String[] value = {tripId, routeId};
//        return makePostHTTPRequest(context, URL_GET_BOOKINGS_FOR_TRIP, key, value, false);
//    }
//
//
//    public static String sendBookingsStatus(Context mContext, JSONObject bookingsStatusObj, String tripId) {
//        String[] key = {"data", "tripId", "currentDeviceTime"};
//        String[] value = {bookingsStatusObj.toString(), tripId, String.valueOf(System.currentTimeMillis() / 1000)};
//        return makeCachablePostHTTPRequest(mContext, URL_BOARDING_STATUS_BY_DRIVER, key, value, false);
//    }
//
//
//    public static String getDriverPersonalDetails(Context mContext, String driverId) {
//        String[] key = {};
//        String[] value = {};
//        return makePostHTTPRequest(mContext, URL_GET_DRIVER_PERSONAL_DETAILS, key, value, false);
//    }
//
//    public static String getPaymentDetails(Context mContext, String vehicleNo) {
//        String[] key = {"vehicleNumber"};
//        String[] value = {vehicleNo};
//        return makePostHTTPRequest(mContext, URL_GET_PAYMENT_DETAILS, key, value, false);
//    }
//
//    public static String driverLogOutToServer(Context mContext, final String imei) {
//        String[] key = {"imei"};
//        String[] value = {imei};
//        return makePostHTTPRequest(mContext, URL_DRIVER_LOGOUT, key, value, true);
//    }
//
//    public static String verifyDriverDeviceSim(Context mContext, final String imei, final String simNo, final String pNumber, final String modelNo, final String macAddress) {
//        String[] key = {"imei", "simNumber", "number", "modelNo", "macAddress"};
//        String[] value = {imei, simNo, pNumber, modelNo, macAddress};
//        return makePostHTTPRequest(mContext, URL_VERIFY_DRIVER_DEVICE_SIM, key, value, true);
//    }
//
//    public static String getDriverVehiclesDetails(Context mContext) {
//        String[] key = {};
//        String[] value = {};
//        return makePostHTTPRequest(mContext, URL_GET_DRIVER_VEHICLE_DETAILS, key, value, true);
//    }
//
//    public static String driverConfirmationCall(Context mContext, final String driverName, final String vehicelId, final int ivrCallRetry, final String imei, final String simNo) {
//        String[] key = {"name", "vehicleId", "ivrCall", "imei", "simNumber"};
//        String[] value = {driverName, vehicelId, ivrCallRetry + "", imei, simNo};
//        return makePostHTTPRequest(mContext, URL_DRIVER_CONFIRMATION_CALL, key, value, false);
//    }
//
//
//    public static String storeDriverStartTripTime(Context mContext, final long tripStartedTime, final String tripStartedLat, final String tripStartedLng, final String tripId, final String type) {
//        String[] key = {"startedTime", "startedLat", "startedLng", "tripId", "type"};
//        String[] value = {tripStartedTime + "", tripStartedLat, tripStartedLng, tripId, type};
//        return makeCachablePostHTTPRequest(mContext, URL_STORE_DRIVER_START_TRIP_TIME, key, value, true);
//
//    }
//
//    public static String storeDriverEndTripTime(Context mContext, final long tripEndedTime, final String tripEndedLat, final String tripEndedLng, final String tripId, final long tx, final long rx, final long totalTx, final long toalRx, final long elapsedRealTime) {
//        String[] key = {"endedTime", "endedLat", "endedLng", "tripId", "tx", "rx", "totalTx", "totalRx", "elapsedTime"};
//        String[] value = {tripEndedTime + "", tripEndedLat, tripEndedLng, tripId, String.valueOf(tx), String.valueOf(rx), String.valueOf(totalTx), String.valueOf(toalRx), String.valueOf(elapsedRealTime)};
//        return makePostHTTPRequest(mContext, URL_STORE_DRIVER_END_TRIP_TIME, key, value, true);
//
//    }
//
//    public static String makeCachablePostHTTPRequest(Context context, String url, String[] key, String[] value, Boolean logApi) {
//        if (isInternetConnected(context)) {
//            return makePostHTTPRequest(context, url, key, value, logApi);
//        } else if (!isInternetConnected(context)) {
//            Request request = new Request();
//            request.URL = url;
//            request.keys = key;
//            request.values = value;
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.create();
//            String requestToSend = gson.toJson(request);
//            Log.d("con_man", gson.toJson(request));
//            Long currentSystemTime = System.currentTimeMillis() / 1000;
//            int sent = 0;
//            DAO.getHelper(context).putCachedData(currentSystemTime, requestToSend, sent);
//            String response = "cached";
//            return response;
//
//
//        }
//        return null;
//
//    }
//
//    public static String verifyNumberByOtp(Context mContext, final String simSerialNumber, final String otp) {
//        String[] key = {"ssid", "otp"};
//        String[] value = {simSerialNumber, otp};
//        return makePostHTTPRequest(mContext, URL_VERIFY_NUMBER_BY_OTP, key, value, true);
//    }
//
//
//    public static String markOfflineBoarding(Context mContext, final String type, final String tripId, final String routeId, final String chirpId, final String boardingTime) {
//        String[] key = {"type", "tripId", "routeId", "chirpId", "boardingTime", "currentDeviceTime"};
//        String[] value = {type, tripId, routeId, chirpId, boardingTime, String.valueOf(System.currentTimeMillis() / 1000)};
//        return makeCachablePostHTTPRequest(mContext, URL_MARK_OFFLINE_BOARDING, key, value, true);
//
//    }
}
