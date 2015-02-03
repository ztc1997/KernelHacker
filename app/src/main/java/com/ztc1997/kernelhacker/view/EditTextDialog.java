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
	//View backView;
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
		//backView = findViewById(R.id.dialog_rootView);
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
                    value = Integer.parseInt(s.toString());
                    if (value < min)
                        value = min;
                    if (value > max)
                        value = max;
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
                editText.setText(value + "");
            }
        });
        
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(value - interval + "");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(value + interval + "");
            }
        });
        
/*        backView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() < view.getLeft() 
						|| event.getX() >view.getRight()
						|| event.getY() > view.getBottom() 
						|| event.getY() < view.getTop()) {
					dismiss();
				}
				return false;
			}
		});*/
		
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
        
        editText.setText(value + "");
	}
/*	
	@Override
	public void show() {
		// TODO 自动生成的方法存根
		super.show();
		// set dialog enter animations
		view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
		backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
	}*/
	
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
/*	
	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.post(new Runnable() {
					@Override
					public void run() {
			        	EditTextDialog.super.dismiss();
			        }
			    });
				
			}
		});
		Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);
		
		view.startAnimation(anim);
		backView.startAnimation(backAnim);
	}*/

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
        if (editText != null)
            editText.setText(value + "");
        return this;
    }

    public static interface OnEditTextSetListener{
        public abstract void onEditTextSet(int edit);
    }

}
