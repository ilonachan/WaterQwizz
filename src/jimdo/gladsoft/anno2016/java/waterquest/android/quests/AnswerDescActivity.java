package jimdo.gladsoft.anno2016.java.waterquest.android.quests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import jimdo.gladsoft.anno2016.java.waterquest.R;

/**
 * @author fgast34
 * @version ??? - 15.07.2016.
 */
public class AnswerDescActivity extends Activity {

    TextView mDesc;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String extraDesc = getIntent().getStringExtra("desc");
        if(extraDesc == null) finish();

        setContentView(R.layout.answerdesc_layout);

        mDesc = (TextView) findViewById(R.id.ExtraDescField);
        mButton = (Button) findViewById(R.id.buttonClose);

        mDesc.setText(extraDesc);

        Activity c = this;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finish();
            }
        });
    }
}
