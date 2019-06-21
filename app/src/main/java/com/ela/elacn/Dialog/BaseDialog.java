package com.ela.elacn.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ela.elacn.R;

public class BaseDialog extends Dialog implements View.OnClickListener {

    private View confirmButton;
    private View closeButton;

    private Context mContext;
    private int layout;

    public BaseDialog(Context context, int layout) {
        super(context, R.style.Widget_AppCompat_ProgressBar_Horizontal);
        this.mContext = context;
        this.layout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        setCanceledOnTouchOutside(false);

        confirmButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        afterCreate();
    }

    //set the value for view.
    protected void afterCreate() {

    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }

}
