package com.ztc1997.kernelhacker.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Slider;
import com.ztc1997.kernelhacker.R;

public class EditTextDialog extends AlertDialog{
    
    private int min, max = 100, interval = 1, value;
    private Slider slider;
    private Button addBtn, subBtn;
    private EditText editText;
    private OnEditTextSetListener onEditTextSetListener;
	
	Context context;
	View view;
	String title;
	TextView titleTextView;
	
	ButtonFlat buttonAccept;
	ButtonFlat buttonCancel;
	
	public EditTextDialog(Context context, String title) {
		super(context);
		this.context = context;// init Context
		this.title = title;
	}
	
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.dialog_edit_text);

        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
	    
		view = findViewById(R.id.contentDialog);
        editText = (EditText) findViewById(R.id.dialog_edit_edit);
        slider = (Slider) findViewById(R.id.dialog_edit_slider);
        subBtn = (Button) findViewById(R.id.dialog_edit_sub);
        addBtn = (Button) findViewById(R.id.dialog_edit_add);
        
        slider.setMax(max);
        slider.setMin(min);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int i = Integer.parseInt(s.toString());
                    value = i;
                    if (i < min)
                        value = min;
                    if (i > max)
                        value = max;
                    if (slider != null)
                        slider.setValue(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        slider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                setValue(value);
            }
        });
        
        subBtn.setClickAfterRipple(false);
        addBtn.setClickAfterRipple(false);
        
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue(value - interval);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue(value + interval);
            }
        });
		
	    this.titleTextView = (TextView) findViewById(R.id.title);
	    setTitle(title);
	    
	    this.buttonAccept = (ButtonFlat) findViewById(R.id.button_accept);
	    buttonAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
                if (onEditTextSetListener != null)
                    onEditTextSetListener.onEditTextSet(Integer.parseInt(editText.getText().toString()));
			}
		});
    
        this.buttonCancel = (ButtonFlat) findViewById(R.id.button_cancel);
        this.buttonCancel.setVisibility(View.VISIBLE);
        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        subBtn.setRippleSpeed(8f);
        addBtn.setRippleSpeed(8f);
        buttonAccept.setRippleSpeed(20f);
        buttonCancel.setRippleSpeed(20f);
        
        setValue(value);
	}
	
	// GETERS & SETTERS
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(title == null)
			titleTextView.setVisibility(View.GONE);
		else{
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
	}

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public void setTitleTextView(TextView titleTextView) {
		this.titleTextView = titleTextView;
	}

	public ButtonFlat getButtonAccept() {
		return buttonAccept;
	}

	public void setButtonAccept(ButtonFlat buttonAccept) {
		this.buttonAccept = buttonAccept;
	}

	public ButtonFlat getButtonCancel() {
		return buttonCancel;
	}

	public void setButtonCancel(ButtonFlat buttonCancel) {
		this.buttonCancel = buttonCancel;
	}

    public EditTextDialog setOnEditTextSetListener(OnEditTextSetListener onEditTextSetListener) {
        this.onEditTextSetListener = onEditTextSetListener;
        return this;
    }

    public int getMin() {
        return min;
    }

    public EditTextDialog setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMax() {
        return max;
    }

    public EditTextDialog setMax(int max) {
        this.max = max;
        return this;
    }

    public int getInterval() {
        return interval;
    }

    public EditTextDialog setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getValue() {
        return value;
    }

    public EditTextDialog setValue(int value) {
        this.value = value;
        if (value < min)
            this.value = min;
        if (value > max)
            this.value = max;
        if (editText != null && !editText.getText().toString().equals(this.value + ""))
            editText.setText(this.value + "");
        if (slider != null)
            slider.setValue(this.value);
        return this;
    }

    public static interface OnEditTextSetListener{
        public abstract void onEditTextSet(int edit);
    }

}
