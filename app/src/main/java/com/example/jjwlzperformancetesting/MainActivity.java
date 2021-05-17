package com.example.jjwlzperformancetesting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amazonaws.util.StringUtils;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.AmplifyConfiguration;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.WavelengthPerformanceResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tvPingResult;
    private TextView tvIperfResult;
    private TextView tvProgressStatus;
    private TextView rCount;

    // 전역변수 선언
    String selectedNetwork = "";
    String selectedInout = "";
    boolean boostZone = false;
    String location = "";
    String locationNm = "";
    String radioNetwork = "";
    String radioStrength = "";
    String radioStatus = "";
    String[] servers = {Const.DAEJEON, Const.PUSAN, Const.P_SEOUL};
    String[] serversNm = {Const.DAEJEON_L, Const.PUSAN_L, Const.P_SEOUL_L};
    String[] latencyAvg = {"", "", ""};
    String[] latencyMin = {"", "", ""};
    String[] latencyMax = {"", "", ""};
    String[] latencyMdev = {"", "", ""};
    String[] tputTcpDown = {"", "", ""};
    String[] tputTcpUp = {"", "", ""};
    String[] tputUdpDown = {"", "", ""};
    String[] tputUdpUp = {"", "", ""};
    Object[] lts = {new String[100], new String[100], new String[100]};
    int progressCount =  180; // 30+(50*3);
    int currentCount = 0;
    // 비동기처리 제어 flag
    boolean thread1 = false;
    boolean thread2 = false;


    ProgressBar simpleProgressBar;

    private GpsTracker gpsTracker;
    private static boolean IS_LTE = false;
    private static boolean IS_5G = false;
    private static final String UNAVAILABLE = "(N/A)";
    private static final String DBM = "dBm";
    private static final String DB = "dB";

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler mainHandler = new Handler();

        try {
            Amplify.addPlugin(new AWSApiPlugin()); // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(new AWSDataStorePlugin());
            AmplifyConfiguration.builder(getApplicationContext()).devMenuEnabled(false).build();
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }

        // initiate progress bar and start button
        // visible the progress bar
        // JJ Alreay did. set visiility false in properties.
        // TODO
        // simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        // simpleProgressBar.setVisibility(View.INVISIBLE);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }

        final TextView textview_address = (TextView) findViewById(R.id.locationStr);

        Button ShowLocationButton = (Button) findViewById(R.id.getLocation);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                textview_address.setText(address + "\n" + "위도: " + latitude + ",  경도: " + longitude);

                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
            }
        });



        // TODO 버튼 클릭 시 프로그레스바 표시, 오버라이드 되면 onClick시 iPerfTest 메소드를 실행하지 않고 오버라이드 내용만 수행해 버림...
        /*
        Button iperfButton = (Button) findViewById(R.id.iperfTest);
        iperfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
                simpleProgressBar.setVisibility(View.VISIBLE);
                simpleProgressBar.setMax(40);
                setProgressValue(progress);
            }
        });
        */

        tvPingResult = findViewById(R.id.pingResult);
        tvIperfResult = findViewById(R.id.iperfResult);
        tvProgressStatus = findViewById(R.id.progressStatus);
        Button buttonTestAll = (Button) findViewById(R.id.testAll);

        buttonTestAll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {

                rCount = findViewById(R.id.rCount);
                System.out.println("@@@@@@ count : " + rCount.getText().toString());
                // TODO 플래그 두고 비동기 처리 제어, 횟수만큼 반복


                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String resultPing = "";
                        try {
                            resultPing = runPingTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String finalResultPing = resultPing;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvPingResult.setText(finalResultPing);
                            }
                        });

                        String resultIperf = "";
                        try {
                            resultIperf = runIperfTest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String finalResultIperf = resultIperf;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvIperfResult.setText(finalResultIperf);
                                Toast.makeText(getApplicationContext(), "done!", Toast.LENGTH_LONG).show();
                            }
                        });

                        sendData(v);
                    }
                });
                t.start();

                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int currentCount = 0;
                        for (int i = 0; i < progressCount; i++) {
                            currentCount = currentCount + 1;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            int finalCurrentCount = currentCount;
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < progressCount; i++) {
                                        tvProgressStatus.setText(String.valueOf(finalCurrentCount) + "/" + String.valueOf(progressCount));
                                    }
                                }
                            });
                        }

                    }
                });
                t2.start();

                /*
                executePing(v);
                try {
                    executeIperf(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendData(v);
                */

                Toast.makeText(MainActivity.this, "start!", Toast.LENGTH_LONG).show();
                simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
                simpleProgressBar.setVisibility(View.VISIBLE);
                simpleProgressBar.setMax(progressCount);
                setProgressValue(0);

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int i = msg.what;
            Toast.makeText(getApplicationContext(), "test" + i, Toast.LENGTH_SHORT).show();
        }
    };

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString();

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void getNetworkName(View v) {
        // 권한체크, tm.getNetworkType()
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러");
        }

        SignalStatus.getNetworkTypeName(tm.getNetworkType());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getSignalStatus(View v) {

        String networkType = "";
        int sst = 0;
        int rsrp = 0;
        int rsrq = 0;
        int rssi = 0;
        int rssnr = 0;
        int csiRsrp = 0;
        int csiRsrq = 0;
        int csiSinr = 0;
        int ssRsrp = 0;
        int ssRsrq = 0;
        int ssSinr = 0;
        String signalStatus = "";

        // 권한체크, tm.getNetworkType()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러 - READ_PHONE_STATE - 설정,어플리케이션,앱이름,권한,전화 허");
            return;
        }

        // 권한체크, tm.getAllCellInfo()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // TODO - debug
            System.out.println("권한체크 에러 - ACCESS_FINE_LOCATION");
            return;
        }

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        networkType = SignalStatus.getNetworkTypeName(tm.getNetworkType());
        int nrStatus = getNrStatus(getApplicationContext());
        System.out.println("@@@@@@ nrStatus : " + nrStatus);
        if ("LTE".equals(networkType) && nrStatus > 0 ) {
            networkType = "NR (" + nrStatus + ")" ;
        }

        TextView networkMode = (TextView) findViewById(R.id.networkMode);
        networkMode.setText("Network Mode: " + networkType);

        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        // TODO - debug
        System.out.println("@@@@  " + cellInfoList.size());

        if (cellInfoList != null) {
            for (int i = 0; i < cellInfoList.size(); i++) {
                if (cellInfoList.get(i).isRegistered()) {
                    if (cellInfoList.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfoList.get(i);
                        CellSignalStrengthWcdma cs = cellInfoWcdma.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm cellInfogsm = (CellInfoGsm) cellInfoList.get(i);
                        CellSignalStrengthGsm cs = cellInfogsm.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfoList.get(i);
                        CellSignalStrengthCdma cs = cellInfoCdma.getCellSignalStrength();
                        sst = cs.getDbm();
                    } else if (cellInfoList.get(i) instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfoList.get(i);
                        CellSignalStrengthLte cs = cellInfoLte.getCellSignalStrength();
                        sst = cs.getDbm();
                        signalStatus = getSignalStatusLTE(cs);
                    } else if (cellInfoList.get(i) instanceof CellInfoNr) {
                        CellInfoNr cellInfoNr = (CellInfoNr) cellInfoList.get(i);
                        CellSignalStrengthNr cs = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();
                        sst = cs.getDbm();
                        signalStatus = getSignalStatusNR(cs);
                    }
                }
            }
        }

        TextView signalStrength = (TextView) findViewById(R.id.signalStrength);
        signalStrength.setText(String.valueOf("Signal Strength: " + sst + " " + DBM));

        System.out.println("Signal Status :" + signalStatus);
        TextView signalStatusStr = (TextView) findViewById(R.id.signalStatusStr);
        signalStatusStr.setText(signalStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getSignalStatusLTE(CellSignalStrengthLte cs) {

        StringBuilder sb = new StringBuilder();
        sb.append("RSRP/Rssi/RSRQ/Rssnr: ");

        sb.append(cs.getRsrp() + " " + DBM);

        sb.append("/");
        if (isUnavailable(cs.getRssi())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRssi() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRsrq());
        }

        sb.append("/");
        if (isUnavailable(cs.getRssnr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getRssnr());
        }
        return sb.toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getSignalStatusNR(CellSignalStrengthNr cs) {

        StringBuilder sb = new StringBuilder();
        sb.append("CSI RSRP/RSRQ/SINR: ");

        if (isUnavailable(cs.getCsiRsrp())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiRsrp() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getCsiRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiRsrq() + " " + DB);
        }

        if (isUnavailable(cs.getCsiSinr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getCsiSinr() + " " + DB);
        }

        sb.append("\\r\\n");
        sb.append("SS RSRP/RSRQ/SINR: ");

        if (isUnavailable(cs.getSsRsrp())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsRsrp() + " " + DBM);
        }

        sb.append("/");
        if (isUnavailable(cs.getSsRsrq())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsRsrq() + " " + DB);
        }

        if (isUnavailable(cs.getSsSinr())) {
            sb.append(UNAVAILABLE);
        } else {
            sb.append(cs.getSsSinr() + " " + DB);
        }
        return sb.toString();
    }

    private static boolean isUnavailable(int i) {
        if (i == 2147483647) {
            return true;
        } else {
            return false;
        }
    }

    public void executePing(View v) throws IOException {

        System.out.println("@@실행됨");

        String command = "ping -A -W 50 -c 100 ";
        String resultsAvg = "";
        String resultsMinMax = "";
        String result = "";

        for (int i = 0; i < this.servers.length; i++) {

            //TODO Iperf 5초 for NR연결
            executeUpload5sForNRConnect(this.servers[i]);

            ShellExecutor se = new ShellExecutor();
            String[] re = se.execute(command + this.servers[i], true);
            result = re[0];
            setLatencies(re[1], i);

            String[] strs = result.split(",");
            // strs 가공, average, min/max/mdev
            String avg = strs[0].split("=")[1].split("/")[1];
            String min = strs[0].split("=")[1].split("/")[0].trim();
            String max = strs[0].split("=")[1].split("/")[2];
            String mdev = strs[0].split("=")[1].split("/")[3].split(" ")[0].trim();

            this.latencyAvg[i] = avg;
            this.latencyMin[i] = min;
            this.latencyMax[i] = max;
            this.latencyMdev[i] = mdev;

            resultsAvg = resultsAvg + "[" + setRPad(serversNm[i], 13, " ") + "] avg = " + avg + "\n";
            resultsMinMax = resultsMinMax + "[" + setRPad(serversNm[i], 13, " ") + "] min/max/mdev = " + min.split("\\.")[0] + "/" + max.split("\\.")[0] + "/" + mdev.split("\\.")[0] + "\n";
        }

        TextView tv = (TextView) findViewById(R.id.pingResult);
        tv.setText(resultsAvg + "\n" + resultsMinMax);
    }

    public void executeIperf(View v) throws IOException {

        ShellExecutor se = new ShellExecutor();

        // app/assets/iperf3파일을 핸드폰으로 복사
        copyAssets(IperfUtil.IPERF3);

        // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/iperf3"
        se.execute("chmod 755 " + Const.PATH + IperfUtil.IPERF3);

        // iperf3 실행 "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3  -c 223.62.93.226  udp -b 1G -t 2 –J -R"
        // String results = se.execute2(IperfUtil.getUdpUploadCommand(IperfUtil.PUSAN));
        String resultTxt = "";
        String results = null;
        String tcp_download = null;
        String tcp_upload = null;
        String udp_download = null;
        String udp_upload = null;

        for (int i = 0; i < this.servers.length; i++) {
            // 변수 초기
            results = "";
            tcp_download = "";
            tcp_upload = "";
            udp_download = "";
            udp_upload = "";

            String server = this.servers[i];

            //TODO Iperf 5초 for NR연결
            se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.UPLOAD, 5));

            // TCP Download
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.DOWNLOAD));

            tcp_download = IperfUtil.getBandwidth(results)[1];
            System.out.println("TCP Download Bandwidth:   " + tcp_download);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // TCP Upload
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.UPLOAD));
            tcp_upload = IperfUtil.getBandwidth(results)[0];
            System.out.println("TCP Upload Bandwidth:   " + tcp_upload);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // UDP Download
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.UDP, IperfUtil.DOWNLOAD));
            udp_download = IperfUtil.getBandwidth(results)[1];
            System.out.println("UDP Download Bandwidth:   " + udp_download);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // UDP Upload
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.UDP, IperfUtil.UPLOAD));
            udp_upload = IperfUtil.getBandwidth(results)[0];
            System.out.println("UDP Upload Bandwidth:   " + udp_upload);

            this.tputTcpDown[i] = tcp_download;
            this.tputTcpUp[i] = tcp_upload;
            this.tputUdpDown[i] = udp_download;
            this.tputUdpUp[i] = udp_upload;

            resultTxt = resultTxt + "[" + setRPad(serversNm[i], 13, " ") + "]" + "\n"
                    + "TCP Download: " + tcp_download + "Mbits/sec, TCP Upload: " + tcp_upload + " Mbits/sec" + "\n"
                    + "UDP Download: " + udp_download + "Mbits/sec, UDP Upload: " + udp_upload + " Mbits/sec" + "\n";
        }

        TextView tv = (TextView) findViewById(R.id.iperfResult);
        tv.setText(resultTxt);
    }

    /*
    public void executeNetperf(View v) throws IOException {

        // app/assets/netperf 파일을 핸드폰으로 복사
        String fileName = "netperf";
        String fullName  = "/data/user/0/com.example.jjwlzperformancetesting/files/netperf";
        String command = fullName + " -H 223.62.93.226 -l 50 -t UDP_RR -o mean_latency,min_latency,max_latency,p50_latency,p90_latency,p99_latency,stddev_latency";
        ShellExecutor se = new ShellExecutor();

        // app/assets/netperf 파일을 핸드폰으로 복사
        copyAssets(fileName);

        // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/netperf"
        se.execute("chmod 755 " + fullName);

        // netperf 실행 "/data/user/0/com.example.jjwlzperformancetesting/files/netperf -H 223.62.93.226 -p 12865 -l -50 -t TCP_RR -j -- -o mean_latency,min_latency,max_latency,p50_latency,p90_latency,p99_latency,stddev_latency"
        String commandResult = se.execute2(command);
        System.out.println("executeCmdNetperf결과:   " + commandResult);

        TextView tv = (TextView) findViewById(R.id.netperfResult);
        tv.setText(commandResult);

    }*/

        public void sendData(View v) {
            System.out.println("@@ Start Method: sendData");

            String selectedNetwork = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.rgNetwork)).getCheckedRadioButtonId())).getText().toString();
            String selectedInout = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.rgInout)).getCheckedRadioButtonId())).getText().toString();
            boolean boostZone = ((ToggleButton) findViewById(R.id.boostZone)).isChecked();
            this.selectedNetwork = selectedNetwork;
            this.selectedInout = selectedInout;
            this.boostZone = boostZone;

            TextView tv = null;

            tv = (TextView) findViewById(R.id.locationStr);
            String locationStr = tv.getText().toString();
            this.location = tv.getText().toString().split("\n")[1].replaceAll("위도: ", "").replaceAll("경도: ", "").trim();
            this.locationNm = tv.getText().toString().split("\n")[0];

            tv = (TextView) findViewById(R.id.networkMode);
            String networkMode = tv.getText().toString();
            this.radioNetwork = tv.getText().toString().replaceAll("Network Mode: ", "");

            tv = (TextView) findViewById(R.id.signalStrength);
            String signalStrength = tv.getText().toString();
            this.radioStrength = tv.getText().toString();

            tv = (TextView) findViewById(R.id.signalStatusStr);
            String signalStatus = tv.getText().toString();
            this.radioStatus = tv.getText().toString().replaceAll("Signal Strength: ", "").replaceAll(" dBm", "");

            tv = (TextView) findViewById(R.id.pingResult);
            String pingResult = tv.getText().toString();

            tv = (TextView) findViewById(R.id.iperfResult);
            String iperfResult = tv.getText().toString();

            System.out.println(locationStr);
            System.out.println(networkMode);
            System.out.println(signalStrength);
            System.out.println(signalStatus);
            System.out.println(pingResult);
            System.out.println(iperfResult);
            System.out.println(selectedNetwork);
            System.out.println(selectedInout);

            create();
        }

        private void copyAssets(String filename) throws IOException {

            String appFileDirectory = getFilesDir().getPath();
            String executableFilePath = appFileDirectory + "/iperf3";

            AssetManager assetManager = getAssets();

            InputStream is = null;
            OutputStream os = null;
            Log.d("", "Attempting to copy this file: " + filename); // + " to: " +       assetCopyDestination);

            try {
                is = assetManager.open(filename);
                Log.d("", "outDir: " + appFileDirectory);
                File outFile = new File(appFileDirectory, filename);
                System.out.println("@@@@@@@@@@@@:  " + outFile.getAbsolutePath());
                os = new FileOutputStream(outFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (IOException e) {
                Log.e("", "Failed to copy asset file: " + filename, e);
            } finally {
                is.close();
                is = null;
                os.flush();
                os.close();
                os = null;
            }

            Log.d("", "Copy success: " + filename);
        }

        private static void copyFileUsingStream(File source, File dest) throws IOException {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(source);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                is.close();
                os.close();
            }
        }

        public void execCommand(View v) {

            String fileName = "iperf3";
            String fullName = "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3";
            String commandStr = "";
            ShellExecutor se = new ShellExecutor();

            // app/assets/iperf3파일을 핸드폰으로 복사
            try {
                copyAssets(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/iperf3"
            se.execute("chmod 755 " + fullName);

            // iperf3 실행 "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3  -c 223.62.93.226  udp -b 1G -t 2 –J -R"
//        commandStr = fullName + " -c 223.62.93.226  udp -b 1G -t 2 –J -R";
//        System.out.println(se.execute(commandStr));

            String[] command = {fullName, "-c", "223.62.93.226"};

            new Thread(() -> {
                try {
                    ProcessBuilder builder = new ProcessBuilder(command);
                    builder.redirectErrorStream(true);
                    final Process proc = builder.start();
                    GetOutput(proc);
            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
            */
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        private static void GetOutput(final Process process) {
            new Thread() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = null;
                    try {
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        private void setProgressValue(final int progress) {

            // set the progress
            simpleProgressBar.setProgress(progress, true);

            // thread is used to change the progress value
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setProgressValue(progress + 1);
                }
            });
            thread.start();
        }

        // RPAD
        private static String setRPad(String strContext, int iLen, String strChar) {
            String strResult = "";
            StringBuilder sbAddChar = new StringBuilder();
            for (int i = strContext.length(); i < iLen; i++) {
                // iLen길이 만큼 strChar문자로 채운다.
                sbAddChar.append(strChar);
            }
            // RPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
            strResult = strContext + sbAddChar;
            return strResult;
        }

        private void createWithAmplify() {
            for (int i = 0; i < servers.length; i++) {
                WavelengthPerformanceResult item = WavelengthPerformanceResult.builder()
                        .location(this.location)
                        .locationNm(this.locationNm)
                        .selectedNetwork(this.selectedNetwork)
                        .selectedInout(this.selectedInout)
                        .radioNetwork(this.radioNetwork)
                        .radioStrength(this.radioStrength)
                        .radioStatus(this.radioStatus)
                        .server(this.servers[i])
                        .serverNm(this.serversNm[i])
                        .latencyAvg(this.latencyAvg[i])
                        .latencyMax(this.latencyMax[i])
                        .latencyMin(this.latencyMin[i])
                        .latencyMdev(this.latencyMdev[i])
                        .tputTcpUp(this.tputTcpDown[i])
                        .tputTcpDown(this.tputTcpUp[i])
                        .tputUdpUp(this.tputUdpDown[i])
                        .tputUdpDown(this.tputUdpUp[i])
                        .selectedBoostzone(this.boostZone)
                        .build();
                Amplify.DataStore.save(
                        item,
                        success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                        error -> Log.e("Amplify", "Could not save item to DataStore", error)
                );
            }
        }


    private void create() {
        System.out.println("@@ Start Method: create");

        String boostZone = (this.boostZone) ? "True" : "False";
        String latitude = this.location.split(",")[0].trim();
        String longitude = this.location.split(",")[1].trim();

        for (int i = 0; i < servers.length; i++) {
            String[] lt = (String[]) this.lts[i];
            DBInsertTask insertTask = new DBInsertTask();
            System.out.println("@@ locationNm :" +this.locationNm);
            insertTask.execute(this.latencyAvg[i], this.latencyMax[i], this.latencyMdev[i], this.latencyMin[i], this.location, latitude, longitude,
                    this.locationNm, this.radioNetwork, this.radioStatus, this.radioStrength, boostZone, this.selectedInout, this.selectedNetwork, servers[i], serversNm[i],
                    this.tputTcpDown[i], this.tputTcpUp[i], this.tputUdpDown[i], this.tputUdpUp[i],
                    lt[0], lt[1], lt[2], lt[3], lt[4], lt[5], lt[6], lt[7], lt[8], lt[9], lt[10], lt[11], lt[12], lt[13], lt[14], lt[15], lt[16], lt[17], lt[18], lt[19],
                    lt[20], lt[21], lt[22], lt[23], lt[24], lt[25], lt[26], lt[27], lt[28], lt[29], lt[30], lt[31], lt[32], lt[33], lt[34], lt[35], lt[36], lt[37], lt[38], lt[39],
                    lt[40], lt[41], lt[42], lt[43], lt[44], lt[45], lt[46], lt[47], lt[48], lt[49], lt[50], lt[51], lt[52], lt[53], lt[54], lt[55], lt[56], lt[57], lt[58], lt[59],
                    lt[60], lt[61], lt[62], lt[63], lt[64], lt[65], lt[66], lt[67], lt[68], lt[69], lt[70], lt[71], lt[72], lt[73], lt[74], lt[75], lt[76], lt[77], lt[78], lt[79],
                    lt[80], lt[81], lt[82], lt[83], lt[84], lt[85], lt[86], lt[87], lt[88], lt[89], lt[90], lt[91], lt[92], lt[93], lt[94], lt[95], lt[96], lt[97], lt[98], lt[99]
            );
/*
            if (i < servers.length - 1) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
*/

        }
/*
        String url = "http://13.125.28.176/wlz/try.php";
        DatabaseInsertTask dbInsert = new DatabaseInsertTask(url, null);
        dbInsert.execute();
*/
    }


    // TODO 여기서부터 refactoring 대상
    public String runPingTest() throws IOException {

        Log.d(TAG, "pingTest시작");

        String command = "ping -A -W 50 -c 100 ";
        String resultsAvg = "";
        String resultsMinMax = "";
        String result = "";

        for (int i = 0; i < this.servers.length; i++) {
            //TODO Iperf 5초 for NR연결
            executeUpload5sForNRConnect(this.servers[i]);

            ShellExecutor se = new ShellExecutor();
            String[] re =  se.execute(command + this.servers[i], true);
            result = re[0];
            setLatencies(re[1], i);

            String[] strs = result.split(",");
            // strs 가공, average, min/max/mdev
            String avg = strs[0].split("=")[1].split("/")[1];
            String min = strs[0].split("=")[1].split("/")[0].trim();
            String max = strs[0].split("=")[1].split("/")[2];
            String mdev = strs[0].split("=")[1].split("/")[3].split(" ")[0].trim();

            this.latencyAvg[i] = avg;
            this.latencyMin[i] = min;
            this.latencyMax[i] = max;
            this.latencyMdev[i] = mdev;

            resultsAvg = resultsAvg + "[" + setRPad(serversNm[i], 13, " ") + "] avg = " + avg + "\n";
            resultsMinMax = resultsMinMax + "[" + setRPad(serversNm[i], 13, " ") + "] min/max/mdev = " + min.split("\\.")[0] + "/" + max.split("\\.")[0] + "/" + mdev.split("\\.")[0] + "\n";
        }

        return resultsAvg + "\n" + resultsMinMax;
    }

    public String runIperfTest() throws IOException {

        ShellExecutor se = new ShellExecutor();

        // app/assets/iperf3파일을 핸드폰으로 복사
        copyAssets(IperfUtil.IPERF3);

        // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/iperf3"
        se.execute("chmod 755 " + Const.PATH + IperfUtil.IPERF3);

        // iperf3 실행 "/data/user/0/com.example.jjwlzperformancetesting/files/iperf3  -c 223.62.93.226  udp -b 1G -t 2 –J -R"
        // String results = se.execute2(IperfUtil.getUdpUploadCommand(IperfUtil.PUSAN));
        String resultTxt = "";
        String results = null;
        String tcp_download = null;
        String tcp_upload = null;
        String udp_download = null;
        String udp_upload = null;

        for (int i = 0; i < this.servers.length; i++) {
            // 변수 초기
            results = "";
            tcp_download = "";
            tcp_upload = "";
            udp_download = "";
            udp_upload = "";

            String server = this.servers[i];

            //TODO Iperf 5초 for NR연결
            se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.UPLOAD, 5));

            // TCP Download
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.DOWNLOAD));

            tcp_download = IperfUtil.getBandwidth(results)[1];
            System.out.println("TCP Download Bandwidth:   " + tcp_download);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // TCP Upload
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.UPLOAD));
            tcp_upload = IperfUtil.getBandwidth(results)[0];
            System.out.println("TCP Upload Bandwidth:   " + tcp_upload);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // UDP Download
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.UDP, IperfUtil.DOWNLOAD));
            udp_download = IperfUtil.getBandwidth(results)[1];
            System.out.println("UDP Download Bandwidth:   " + udp_download);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // UDP Upload
            results = "";
            results = se.execute(IperfUtil.getCommand(server, IperfUtil.UDP, IperfUtil.UPLOAD));
            udp_upload = IperfUtil.getBandwidth(results)[0];
            System.out.println("UDP Upload Bandwidth:   " + udp_upload);

            this.tputTcpDown[i] = tcp_download;
            this.tputTcpUp[i] = tcp_upload;
            this.tputUdpDown[i] = udp_download;
            this.tputUdpUp[i] = udp_upload;

            resultTxt = resultTxt + "[" + setRPad(serversNm[i], 13, " ") + "]" + "\n"
                    + "TCP Download: " + tcp_download + "Mbits/sec, TCP Upload: " + tcp_upload + " Mbits/sec" + "\n"
                    + "UDP Download: " + udp_download + "Mbits/sec, UDP Upload: " + udp_upload + " Mbits/sec" + "\n";
        }

        // comment
        return resultTxt;
    }

    private void setLatencies(String s, int i) {
        String[] list = (String[]) this.lts[i];
        String[] lines = s.split("\n");
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            if (line.contains("icmp_seq")) {
                int seq = Integer.parseInt(line.split("icmp_seq=")[1].substring(0,3).split(" ")[0]);
                String latency = line.split("time=")[1].substring(0,4);
                list[seq-1] = latency;
            }
        }


        // TODO TMP delete
        String[] tlist = (String[]) this.lts[i];
        for (int k = 0; k < tlist.length; k++) {
            System.out.println("@@@@@@ " + tlist[k]);
        }
    }

    private void executeUpload5sForNRConnect(String server) throws IOException {

        ShellExecutor se = new ShellExecutor();

        // app/assets/iperf3파일을 핸드폰으로 복사
        copyAssets(IperfUtil.IPERF3);

        // 파일권한 변경 "chmod 755 /data/user/0/com.example.jjwlzperformancetesting/files/iperf3"
        se.execute("chmod 755 " + Const.PATH + IperfUtil.IPERF3);

        se.execute(IperfUtil.getCommand(server, IperfUtil.TCP, IperfUtil.UPLOAD, 5));
    }


    private static int getNrStatus(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        int nrStatus = 0;
        Object oServiceState = null;
        try {
            Class cTelephonyManager = Class.forName(tm.getClass().getName());
            Method mGetServiceState = cTelephonyManager.getDeclaredMethod("getServiceState");
            oServiceState = mGetServiceState.invoke(tm);
        } catch (ClassNotFoundException e) {
            nrStatus = -3;
        } catch (NoSuchMethodException e) {
            nrStatus = -4;
        } catch (IllegalAccessException e) {
            nrStatus = -5;
        } catch (InvocationTargetException e) {
            nrStatus = -6;
        }

        if (oServiceState != null) {
            if (Build.VERSION.SDK_INT >= 29) {
                try {
                    Class cServiceState = Class.forName(oServiceState.getClass().getName());
                    Method mToString = cServiceState.getDeclaredMethod("toString");
                    mToString.setAccessible(true);
                    String dumpString = (String) mToString.invoke(oServiceState);
                    int dataInfoStartIndex = dumpString.indexOf("DataSpecificRegistrationInfo");
                    if (dataInfoStartIndex > -1) {
                        int dataInfoEndIndex = dumpString.indexOf("]", dataInfoStartIndex);
                        String subString;
                        if (dataInfoEndIndex > dataInfoStartIndex) {
                            subString = dumpString.substring(dataInfoStartIndex, dataInfoEndIndex);
                        } else {
                            subString = dumpString.substring(dataInfoStartIndex);
                        }

                        final String nrStateParam = "nrState=";
                        final String nrStateClose = "}";
                        int nrStartIndex = subString.indexOf(nrStateParam);
                        if (nrStartIndex > -1) {
                            int nrEndIndex = subString.indexOf(nrStateClose, nrStartIndex);
                            String nrStateString;
                            if (nrEndIndex > nrStartIndex) {
                                nrStateString = subString.substring(nrStartIndex + nrStateParam.length(), nrEndIndex);
                            } else {
                                nrStateString = subString.substring(nrStartIndex + nrStateParam.length());
                            }

                            if (nrStateString.contains("CONNECTED")) {
                                nrStatus = 3;
                            } else if (nrStateString.contains("NOT_RESTRICTED")) {
                                nrStatus = 2;
                            } else if (nrStateString.contains("RESTRICTED")) {
                                nrStatus = 1;
                            } else if (nrStateString.contains("NONE")) {
                                nrStatus = -1;
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    nrStatus = -7;
                } catch (NoSuchMethodException e) {
                    nrStatus = -8;
                } catch (IllegalAccessException e) {
                    nrStatus = -9;
                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
                    nrStatus = -10;
                }
            } else {
                try {
                    Class cServiceState = Class.forName(oServiceState.getClass().getName());
                    Method mGetNrStatus = cServiceState.getDeclaredMethod("getNrStatus");
                    mGetNrStatus.setAccessible(true);
                    Integer oNrStatus = (Integer) mGetNrStatus.invoke(oServiceState);
                    if (oNrStatus != null) {
                        nrStatus = oNrStatus;
                    }
                } catch (ClassNotFoundException e) {
                    nrStatus = -7;
                } catch (NoSuchMethodException e) {
                    nrStatus = -8;
                } catch (IllegalAccessException e) {
                    nrStatus = -9;
                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
                    nrStatus = -10;
                }
            }
        }
        return nrStatus;
    }
}