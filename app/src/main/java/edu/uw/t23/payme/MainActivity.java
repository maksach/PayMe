package edu.uw.t23.payme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.microblink.activity.BlinkOCRActivity;
import com.microblink.ocr.ScanConfiguration;
import com.microblink.recognizers.blinkocr.parser.generic.AmountParserSettings;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;
import com.microblink.view.CameraEventsListener;

public class MainActivity extends AppCompatActivity implements CameraEventsListener {

    private int MY_REQUEST_CODE = 911;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Intent for BlinkOCRActivity Activity
        com.microblink.util.Log.setLogLevel(com.microblink.util.Log.LogLevel.LOG_VERBOSE);

        RecognizerCompatibilityStatus status = RecognizerCompatibility.getRecognizerCompatibilityStatus(this);
        if(status == RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
            Toast.makeText(this, "BlinkOCR is supported!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "BlinkOCR is not supported! Reason: " + status.name(), Toast.LENGTH_LONG).show();
        }




        Intent intent = new Intent(this, BlinkOCRActivity.class);

        // set your licence key
        // obtain your licence key at http://microblink.com/login or
        // contact us at http://help.microblink.com
        intent.putExtra(BlinkOCRActivity.EXTRAS_LICENSE_KEY, "API_KEY_HERE API");

        // setup array of scan configurations. Each scan configuration
        // contains 4 elements: resource ID for title displayed
        // in BlinkOCRActivity activity, resource ID for text
        // displayed in activity, name of the scan element (used
        // for obtaining results) and parser setting defining
        // how the data will be extracted.
        // For more information about parser setting, check the
        // chapter "Scanning segments with BlinkOCR recognizer"
        ScanConfiguration[] confArray = new ScanConfiguration[] {
                new ScanConfiguration(R.string.amount_title, R.string.amount_msg, "Amount", new AmountParserSettings())
//                new ScanConfiguration(R.string.raw_title, R.string.raw_msg, "Raw", new RawParserSettings())
        };
        intent.putExtra(BlinkOCRActivity.EXTRAS_SCAN_CONFIGURATION, confArray);

        // Starting Activity
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == BlinkOCRActivity.RESULT_OK && data != null) {
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                Bundle results = extras.getBundle(BlinkOCRActivity.EXTRAS_SCAN_RESULTS);

                // results bundle contains result strings in keys defined
                // by scan configuration name
                // for example, if set up as in step 1, then you can obtain
                // e-mail address with following line
                String amount = results.getString("Amount");
            }
        }
    }

    @Override
    public void onCameraPreviewStarted() {
        // this method is from CameraEventsListener and will be called when camera preview starts
    }

    @Override
    public void onCameraPreviewStopped() {
        // this method is from CameraEventsListener and will be called when camera preview stops
    }

    @Override
    public void onError(Throwable exc) {
        /**
         * This method is from CameraEventsListener and will be called when
         * opening of camera resulted in exception or recognition process
         * encountered an error. The error details will be given in exc
         * parameter.
         */
    }

    @Override
    @TargetApi(23)
    public void onCameraPermissionDenied() {
        /**
         * Called on Android 6.0 and newer if camera permission is not given
         * by user. You should request permission from user to access camera.
         */
//        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
        /**
         * Please note that user might have not given permission to use
         * camera. In that case, you have to explain to user that without
         * camera permissions scanning will not work.
         * For more information about requesting permissions at runtime, check
         * this article:
         * https://developer.android.com/training/permissions/requesting.html
         */
    }

    @Override
    public void onAutofocusFailed() {
        /**
         * This method is from CameraEventsListener will be called when camera focusing has failed.
         * Camera manager usually tries different focusing strategies and this method is called when all
         * those strategies fail to indicate that either object on which camera is being focused is too
         * close or ambient light conditions are poor.
         */
    }

    @Override
    public void onAutofocusStarted(Rect[] areas) {
        /**
         * This method is from CameraEventsListener and will be called when camera focusing has started.
         * You can utilize this method to draw focusing animation on UI.
         * Areas parameter is array of rectangles where focus is being measured.
         * It can be null on devices that do not support fine-grained camera control.
         */
    }

    @Override
    public void onAutofocusStopped(Rect[] areas) {
        /**
         * This method is from CameraEventsListener and will be called when camera focusing has stopped.
         * You can utilize this method to remove focusing animation on UI.
         * Areas parameter is array of rectangles where focus is being measured.
         * It can be null on devices that do not support fine-grained camera control.
         */
    }

}
