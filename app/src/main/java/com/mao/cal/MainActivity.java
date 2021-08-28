package com.mao.cal;


import android.os.*;
import android.support.v7.app.*;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.app.*;
import android.content.*;

public class MainActivity extends AppCompatActivity
{
	public static boolean flag = false;
	public static MyViewGroup mvg;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mvg = (MyViewGroup)findViewById(R.id.MyViewGroup);
    }

	public void change(View view)
	{
		if(flag)
		{
			flag =  false;
		}else{
			flag = true;
		}
	}
	
	public void update(View view)
	{
		mvg.update();
	}

}


