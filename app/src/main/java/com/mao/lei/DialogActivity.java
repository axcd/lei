package com.mao.lei;
import android.support.v7.app.*;
import android.os.*;
import java.util.*;

public class DialogActivity extends AppCompatActivity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
    }

	@Override
	public void finish()
	{
		// TODO: Implement this method
		super.finish();
		MainActivity.mvg.update();
	}

}
