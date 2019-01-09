package com.example.chaitanyareddy.clats;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class edit_information extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_edit_information);

        btn1 = (Button) findViewById(R.id.bt5);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent anythingintent =new Intent(edit_information.this,StudentSignup.class);
                startActivity(anythingintent);
            }
        });
    }
}
