package com.gwent.bruce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etDefend;
    EditText etDefendArmor;
    EditText etAttack;
    EditText etAttackArmor;
    Button btnCalculate;
    Button btnClean;
    TextView tvBestChoice;
    TextView tvProcess;

    StringBuffer process;
    int total;
    int repeatCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etDefend = (EditText) findViewById(R.id.et_defend);
        etDefendArmor = (EditText) findViewById(R.id.et_defend_Armor);
        etAttack = (EditText) findViewById(R.id.et_attack);
        etAttackArmor = (EditText) findViewById(R.id.et_attack_Armor);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);
        btnClean = (Button) findViewById(R.id.btn_clean);
        tvBestChoice = (TextView) findViewById(R.id.tv_best_choice);
        tvProcess = (TextView) findViewById(R.id.tv_process);

        process = new StringBuffer();

        etDefend.addTextChangedListener(textWatcher);
        etDefendArmor.addTextChangedListener(textWatcher);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueAndCalculate();
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanAll();
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(etDefend.getText().toString())) {
                double defend = Double.valueOf(etDefend.getText().toString());
                if (!TextUtils.isEmpty(etDefendArmor.getText().toString())) {
                    defend = defend + Double.valueOf(etDefendArmor.getText().toString());
                }
                double bestChoice = defend * 0.618;
                tvBestChoice.setText("收益最大的攻击者点数：" + Math.round(bestChoice));
            }
        }
    };

    private void cleanAll() {
        etDefend.setText("");
        etDefendArmor.setText("");
        etAttack.setText("");
        etAttackArmor.setText("");
        tvBestChoice.setText("");
        tvProcess.setText("");
    }

    private void getValueAndCalculate() {
        int defend;
        int attack;
        int defendArmor;
        int attackArmor;

        if (!TextUtils.isEmpty(etDefend.getText().toString())) {
            defend = Integer.valueOf(etDefend.getText().toString());
        } else {
            defend = 0;
        }

        if (!TextUtils.isEmpty(etAttack.getText().toString())) {
            attack = Integer.valueOf(etAttack.getText().toString());
        } else {
            attack = 0;
        }

        if (!TextUtils.isEmpty(etDefendArmor.getText().toString())) {
            defendArmor = Integer.valueOf(etDefendArmor.getText().toString());
        } else {
            defendArmor = 0;
        }

        if (!TextUtils.isEmpty(etAttackArmor.getText().toString())) {
            attackArmor = Integer.valueOf(etAttackArmor.getText().toString());
        } else {
            attackArmor = 0;
        }

        process.delete(0, process.length());
        process.append("决斗过程\n");
        process.append(attack + " --> " + defend + "\n");
        repeatCount = 0;
        total = defend + attack;
        calculate(defend, attack, defendArmor, attackArmor);

    }

    private void calculate(int defend, int attack, int defendArmor, int attackArmor) {

        if (defend ==0 || attack == 0) {
            int result = total - (defend + attack);

            if (repeatCount % 2 == 0) {
                if (defend == 0) {
                    process.insert(0, "攻方存活\n\n");
                } else {
                    process.insert(0, "守方存活\n\n");
                }
            } else {
                if (attack == 0) {
                    process.insert(0, "攻方存活\n\n");
                } else {
                    process.insert(0, "守方存活\n\n");
                }
            }

            process.insert(0 ,"老哥拍出貂蝉的收益: " + result + "+2" + "=" + (result + 2) + "\n");
            tvProcess.setText(process);
            return;
        }
        if (defendArmor != 0) {
            int tmp = attack - defendArmor;
            if (tmp >= 0) {
                defend = defend - tmp;
                defendArmor = 0;
            } else {
                defendArmor = defendArmor - attack;
            }
        } else {
            defend = defend - attack;
        }

        if (defend < 0) {
            defend = 0;
        }
        if (attack < 0) {
            attack = 0;
        }

        process.append(defend + " --> " + attack + "\n");
        repeatCount++;
        calculate(attack, defend, attackArmor, defendArmor);
    }
}
