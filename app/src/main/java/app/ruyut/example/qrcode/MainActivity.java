package app.ruyut.example.qrcode;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得相機權限
        getPermissionCamera();
    }

    /**
     * 自訂相機權限代號，用於判斷是否取得權限
     */
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    /**
     * 取得相機權限
     */
    public void getPermissionCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // 已有相機權限，不須再詢問
            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            // 曾經被使用者拒絕授予權限過，可以在這邊提醒使用者為何需要權限
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("需要相機權限")
                    .setMessage("需要相機權限才能掃描 QR Code，請授予相機權限")
                    .setPositiveButton("OK", (dialog, which) -> {
                                // 再次顯示權限授予視窗
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                            }
                    )
                    .show();
        } else {
            // 第一次詢問權限，或者使用者點選「不再詢問」
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    /**
     * 取得詢問相機權限的結果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 使用者同意授予權限
                    Toast.makeText(this, "已取得相機權限", Toast.LENGTH_SHORT).show();
                } else {
                    // 使用者拒絕授予權限
                    Toast.makeText(this, "未取得相機權限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}