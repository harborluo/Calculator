package com.harbor.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements android.view.View.OnClickListener{


    private TextView resultPanelText ;

    private Button btn_clear ;

    private Button btn_devide ;

    private Button btn_multiply ;

    private Button btn_delete ;

    private Button btn_minus ;

    private Button btn_plus ;

    private Button btn_equal ;

    private Button btn_dot ;

    private Button btn_0, btn_1, btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;

    private boolean clearFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "Android calculator started.");
        Log.d("Calculator", "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultPanelText  = (TextView) this.findViewById(R.id.resultPanelText);

        btn_clear  = (Button) this.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_devide  = (Button) this.findViewById(R.id.btn_devide);
        btn_devide.setOnClickListener(this);

        btn_multiply  = (Button) this.findViewById(R.id.btn_multiply);
        btn_multiply.setOnClickListener(this);
        btn_delete  = (Button) this.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_minus  = (Button) this.findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(this);
        btn_plus  = (Button) this.findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(this);

        btn_equal  = (Button) this.findViewById(R.id.btn_equal);
        btn_equal.setOnClickListener(this);

        btn_dot  = (Button) this.findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);

        btn_0  = (Button) this.findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1  = (Button) this.findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2  = (Button) this.findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3  = (Button) this.findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4  = (Button) this.findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5  = (Button) this.findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6  = (Button) this.findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7  = (Button) this.findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8  = (Button) this.findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9  = (Button) this.findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        String panelText = resultPanelText.getText().toString();

        if(clearFlag==true){
            panelText="";
        }

        String btnText = ((Button) view).getText().toString();
        String result = panelText;
        clearFlag = false;
        switch(view.getId()){
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            {
                result = panelText+btnText;

                break;
            }
            case R.id.btn_0:{
                if(!panelText.startsWith("0")){
                    result = panelText+btnText;
                }
                break;
            }
            case R.id.btn_clear:{
                result = "";
                break;
            }
            case R.id.btn_delete:{
                if(result.length()>0){
                    result = result.substring(0, result.length()-(result.endsWith(" ")? 3 : 1));
                }
                break;
            }
            case R.id.btn_dot:{
                if(result.matches("([0-9\\.]+[ ][\\+\\-\\*\\/][ ])?[0-9]+$")){
                    result = panelText+btnText;
                }
                break;
            }
            case R.id.btn_minus:{
                if(result.isEmpty()){
                    result = btnText;
                }else if(result.matches(".*[0-9]+$")){
                    result = panelText+" "+ btnText+" ";
                }
                break;
            }
            case R.id.btn_plus:
            case R.id.btn_multiply:
            case R.id.btn_devide:{
                if(result.matches(".*[0-9]+$")){
                    result = panelText+" "+ btnText+" ";
                }
                break;
            }case R.id.btn_equal:{
                if(clearFlag==false&&panelText.length()>0){
                    String expression = panelText.replaceAll(" ", "").replaceAll("[^0-9]$", "");
                    Log.i("calculate", "Expression is : " + expression);
                    result = (eval(expression)+"").replaceAll("\\.0$", "");
                    clearFlag = true;
                }
            }


        }

        resultPanelText.setText(result);
    }


    private Toast toast;
    private long lastBackPressTime = 0;

    @Override
    public void onBackPressed() {
        /**

         new AlertDialog.Builder(this)
         .setMessage("Are you sure you want to exit?")
         .setCancelable(false)
         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
         MainActivity.this.finish();
         }
         })
         .setNegativeButton("No", null)
         .show();
         */

        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Press back again to close this app", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }


    private double eval(final String str) {

        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }






//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
