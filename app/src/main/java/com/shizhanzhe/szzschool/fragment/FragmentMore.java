package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.shizhanzhe.szzschool.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/11/4.
 */
@ContentView(R.layout.fragment_more)
public class FragmentMore extends Fragment {
    @ViewInject(R.id.tgbtn_lightness_ctrl)
    ToggleButton mTgLight;
    @ViewInject(R.id.layout_night_mode)
    View mViewNightMode;
    @ViewInject(R.id.seekBar_light)
    SeekBar mSeekBarBrightness;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTgLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    mViewNightMode.setVisibility(View.VISIBLE);
                    initBrightness();
                } else {
                    mViewNightMode.setVisibility(View.GONE);
                    int tmpInt = Settings.System.getInt(getActivity().getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, -1);
                    WindowManager.LayoutParams wl = getActivity().getWindow().getAttributes();
                    float tmpFloat = (float) tmpInt / 255;
                    if (tmpFloat > 0 && tmpFloat <= 1) {
                        wl.screenBrightness = tmpFloat;
                    }
                    getActivity().getWindow().setAttributes(wl);

                }
            }
        });

    }
    /**
     * 亮度调节
     */
    private void initBrightness() {
        // 取得当前亮度
        int normal = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
        // 进度条绑定当前亮度
        mSeekBarBrightness.setProgress(normal);
        mSeekBarBrightness
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // 取得当前进度
                        int tmpInt = seekBar.getProgress();

                        // 当进度小于80时，设置成80，防止太黑看不见的后果。
                        if (tmpInt < 80) {
                            tmpInt = 80;
                        }
                        // 根据当前进度改变亮度
                        WindowManager.LayoutParams wl = getActivity().getWindow()
                                .getAttributes();
                        float tmpFloat = (float) tmpInt / 255;
                        if (tmpFloat > 0 && tmpFloat <= 1) {
                            wl.screenBrightness = tmpFloat;
                        }
                        getActivity().getWindow().setAttributes(wl);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                    }
                });

    }
}
