package com.example.qr_gen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    EditText qrcode_editText;
    ImageView qrcode_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrcode_editText = findViewById(R.id.qrcode_editText);

        qrcode_imageView = findViewById(R.id.qrcode_imageView);

        Button create_qrcode_btn = findViewById(R.id.qrcode_btn);
        create_qrcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //입력 데이터 변수에 담기
                String editText = qrcode_editText.getText().toString().trim();
                String userNumber = "{\"userNumber\":\"" + editText + "\"}";

                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    //1.바코드 생성
                    BitMatrix matrix = writer.encode(userNumber, BarcodeFormat.QR_CODE, 350, 350);

                    //2.바코드엔코더 생성
                    BarcodeEncoder encoder = new BarcodeEncoder();

                    //3. 엔코더로 생성한 코드를 비트맵 객체에 넣기
                    Bitmap bitmap = encoder.createBitmap(matrix);

                    //4. 비트맴을 이미지뷰에 넣기
                    qrcode_imageView.setImageBitmap(bitmap);

                    //5. 입출력 매니저 생성
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    //매니저 객체로 키보드 숨기기
                    manager.hideSoftInputFromWindow(qrcode_editText.getApplicationWindowToken(), 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}