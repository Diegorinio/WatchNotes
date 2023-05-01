package com.example.projektfizyka;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.media.Image;
import android.widget.ImageView;

public class BluetoothStatus {
    boolean BT_status;
    private boolean CheckBluetoothStatus(){
        BluetoothAdapter _BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(_BluetoothAdapter ==null){
            return false;
        }
        else if(_BluetoothAdapter.isEnabled()){
            return true;
        }
        else{
            return false;
        }
    }

    public void ShowBluetoothStatus(ImageView icon){
        this.BT_status = CheckBluetoothStatus();
        if(BT_status){
            icon.setImageResource(R.drawable.ic_baseline_bluetooth_enabled);
        }else{
            icon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);
        }
    }
}
