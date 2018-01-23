
/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.nordicsemi.nrfUARTv2;




import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Arrays;

import com.nordicsemi.nrfUARTv2.UartService;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;

    TextView mRemoteRssiVal;
    RadioGroup mRg;
    private int mState = UART_PROFILE_DISCONNECTED;
    private UartService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private ListView messageListView;
    private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect,btnSend, button1;
    private Button Button_0_0,Button_0_1,Button_0_2,Button_0_3,Button_0_4,Button_1_0,Button_1_1,Button_1_2,Button_1_3,Button_1_4,Button_2_0,Button_2_1,Button_2_2,Button_2_3,Button_2_4,Button_3_0,Button_3_1,Button_3_2,Button_3_3,Button_3_4,Button_4_0,Button_4_1,Button_4_2,Button_4_3,Button_4_4;
    private EditText edtMessage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        messageListView = (ListView) findViewById(R.id.listMessage);
        /*
        Button button1 = (Button) findViewById(R.id.LayoutButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mService.writeRXCharacteristic("hei".getBytes());
            }
        });
        */




        listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);
        messageListView.setAdapter(listAdapter);
        messageListView.setDivider(null);
        btnConnectDisconnect=(Button) findViewById(R.id.btn_select);
        btnSend=(Button) findViewById(R.id.sendButton);

        edtMessage = (EditText) findViewById(R.id.sendText);
        SeekBar seekBar_Red = (SeekBar) findViewById(R.id.seekBar_R);


        button1=(Button) findViewById(R.id.LayoutButton1);

        Button_0_0 =(Button) findViewById(R.id.button1);
        Button_0_1 =(Button) findViewById(R.id.button2);
        Button_0_2 =(Button) findViewById(R.id.button3);
        Button_0_3 =(Button) findViewById(R.id.button4);
        Button_0_4 =(Button) findViewById(R.id.button5);
        Button_1_0 =(Button) findViewById(R.id.button6);
        Button_1_1 =(Button) findViewById(R.id.button7);
        Button_1_2 =(Button) findViewById(R.id.button8);
        Button_1_3 =(Button) findViewById(R.id.button9);
        Button_1_4 =(Button) findViewById(R.id.button10);
        Button_2_0 =(Button) findViewById(R.id.button11);
        Button_2_1 =(Button) findViewById(R.id.button12);
        Button_2_2 =(Button) findViewById(R.id.button13);
        Button_2_3 =(Button) findViewById(R.id.button14);
        Button_2_4 =(Button) findViewById(R.id.button15);
        Button_3_0 =(Button) findViewById(R.id.button16);
        Button_3_1 =(Button) findViewById(R.id.button17);
        Button_3_2 =(Button) findViewById(R.id.button18);
        Button_3_3 =(Button) findViewById(R.id.button19);
        Button_3_4 =(Button) findViewById(R.id.button20);
        Button_4_0 =(Button) findViewById(R.id.button21);
        Button_4_1 =(Button) findViewById(R.id.button22);
        Button_4_2 =(Button) findViewById(R.id.button23);
        Button_4_3 =(Button) findViewById(R.id.button24);
        Button_4_4 =(Button) findViewById(R.id.button25);

        service_init();

       
        // Handle Disconnect & Connect button
        btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBtAdapter.isEnabled()) {
                    Log.i(TAG, "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                else {
                	if (btnConnectDisconnect.getText().equals("Connect")){
                		
                		//Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices
                		
            			Intent newIntent = new Intent(MainActivity.this, DeviceListActivity.class);
            			startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
        			} else {
        				//Disconnect button pressed
        				if (mDevice!=null)
        				{
        					mService.disconnect();
        					
        				}
        			}
                }
            }
        });

        Button_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Led_Set(0,0);
            }
        });
        Button_0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(0,1);
            }
        });
        Button_0_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(0,2);
            }
        });
        Button_0_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(0,3);
            }
        });
        Button_0_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(0,4);
            }
        });
        Button_1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(1,0);
            }
        });
        Button_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(1,1);
            }
        });
        Button_1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(1,2);
            }
        });
        Button_1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(1,3);
            }
        });
        Button_1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(1,4);
            }
        });
        Button_2_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(2,0);
            }
        });
        Button_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(2,1);
            }
        });
        Button_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(2,2);
            }
        });
        Button_2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(2,3);
            }
        });
        Button_2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(2,4);
            }
        });
        Button_3_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(3,0);
            }
        });
        Button_3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(3,1);
            }
        });
        Button_3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(3,2);
            }
        });
        Button_3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(3,3);
            }
        });
        Button_3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(3,4);
            }
        });
        Button_4_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(4,0);
            }
        });
        Button_4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(4,1);
            }
        });
        Button_4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(4,2);
            }
        });
        Button_4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(4,3);
            }
        });
        Button_4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Led_Set(4,4);
            }
        });
        //button 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SeekBar seekBar_Red = (SeekBar) findViewById(R.id.seekBar_R);
                SeekBar seekBar_Green = (SeekBar) findViewById(R.id.seekBar_G);
                SeekBar seekBar_Blue = (SeekBar) findViewById(R.id.seekBar_B);
                //String endend = "1 \n ";
                String endend = ",,1";
                byte[] end_this;
                int[] ValuesArray = new int[7];


                    int Color_R = seekBar_Red.getProgress();
                int Color_G = seekBar_Green.getProgress();
                int Color_B = seekBar_Blue.getProgress();

                    ByteBuffer buf = ByteBuffer.allocate(4);
                //    buf.putInt(message);
                // mService.writeRXCharacteristic(buf.array());

               // for (int i = 0; i < 5; i++) {
              //      ValuesArray[i] = i;
           //     }
                ValuesArray[4] = (byte)Color_R;
                ValuesArray[5] = (byte)Color_G;
                ValuesArray[6] = (byte)Color_B;
                String message2 = Arrays.toString(ValuesArray) + "\n";

                byte[] value;
                try {
                    //send data to service
                    value = message2.getBytes("UTF-8");
                    mService.writeRXCharacteristic(value);
                    //Update the log with time stamp
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    listAdapter.add("["+currentDateTimeString+"] TX: "+ Color_R);
                    messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                    edtMessage.setText("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                    //Update the log with time stamp
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    listAdapter.add("["+currentDateTimeString+"] TX: "+ Color_R);
                    messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);



            }
        });

        //SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar_Red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int message = seekBar.getProgress();

                //send data to service
                ByteBuffer buf = ByteBuffer.allocate(4);
                buf.putInt(message);
              //  mService.writeRXCharacteristic(buf.array());
                //Update the log with time stamp
                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                listAdapter.add("["+currentDateTimeString+"] TX: "+ message);
                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                edtMessage.setText("");
            }
        });

        // Handle Send button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText editText = (EditText) findViewById(R.id.sendText);
                String message = editText.getText().toString();
                byte[] value;
                try {
                    //send data to service
                    value = message.getBytes("UTF-8");
                    mService.writeRXCharacteristic(value);
                    //Update the log with time stamp
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    listAdapter.add("["+currentDateTimeString+"] TX: "+ message);
                    messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                    edtMessage.setText("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        });
     
        // Set initial UI state
        
    }
    
    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
        		mService = ((UartService.LocalBinder) rawBinder).getService();
        		Log.d(TAG, "onServiceConnected mService= " + mService);
        		if (!mService.initialize()) {
                    Log.e(TAG, "Unable to initialize Bluetooth");
                    finish();
                }

        }

        public void onServiceDisconnected(ComponentName classname) {
       ////     mService.disconnect(mDevice);
        		mService = null;
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        
        //Handler events that received from UART service 
        public void handleMessage(Message msg) {
  
        }
    };
    boolean Bluetooth_connected = false;
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
           //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_CONNECT_MSG");
                             btnConnectDisconnect.setText("Disconnect");
                             edtMessage.setEnabled(true);
                             btnSend.setEnabled(true);
                             Bluetooth_connected = true;
                             button1.setEnabled(true);
                             ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - ready");
                             listAdapter.add("["+currentDateTimeString+"] Connected to: "+ mDevice.getName());
                        	 	messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                             mState = UART_PROFILE_CONNECTED;
                     }
            	 });
            }
           
          //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                    	 	 String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_DISCONNECT_MSG");
                             btnConnectDisconnect.setText("Connect");
                             edtMessage.setEnabled(false);
                             btnSend.setEnabled(false);
                             Bluetooth_connected = false;
                             ((TextView) findViewById(R.id.deviceName)).setText("Not Connected");
                             listAdapter.add("["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
                             mState = UART_PROFILE_DISCONNECTED;
                             mService.close();
                            //setUiState();
                         
                     }
                 });
            }
            
          
          //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
             	 mService.enableTXNotification();
            }
          //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
              
                 final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                 runOnUiThread(new Runnable() {
                     public void run() {
                         try {
                         	String text = new String(txValue, "UTF-8");
                         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        	 	listAdapter.add("["+currentDateTimeString+"] RX: "+text);
                        	 	messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                        	
                         } catch (Exception e) {
                             Log.e(TAG, e.toString());
                         }
                     }
                 });
             }
           //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
            	showMessage("Device doesn't support UART. Disconnecting");
            	mService.disconnect();
            }
            
            
        }
    };

    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
  
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
    	 super.onDestroy();
        Log.d(TAG, "onDestroy()");
        
        try {
        	LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        } 
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;
       
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
 
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

        case REQUEST_SELECT_DEVICE:
        	//When the DeviceListActivity return, with the selected device address
            if (resultCode == Activity.RESULT_OK && data != null) {
                String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
               
                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - connecting");
                mService.connect(deviceAddress);
                            

            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        default:
            Log.e(TAG, "wrong request code");
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       
    }

    
    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  
    }

    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("nRFUART's running in background.\n             Disconnect to exit");
        }
        else {
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.popup_message)
            .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
   	                finish();
                }
            })
            .setNegativeButton(R.string.popup_no, null)
            .show();
        }
    }
    Color mColor = new Color();

    //This function takes in position and sends it to the bluetooth device
    public void Led_Set(int X, int Y) {
        int LED_MATRIX_SIZE = 25;
        int LED_MATRIX_SIZE_X = 5;
        int LED_MATRIX_SIZE_Y = 5;

        //LED_MATRIX = new int [LED_MATRIX_SIZE_X][LED_MATRIX_SIZE_Y];
        int[][] LED_MATRIX = {
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24}
        };
        int Position = LED_MATRIX[X][Y];

        SeekBar seekBar_Red = (SeekBar) findViewById(R.id.seekBar_R);
        SeekBar seekBar_Green = (SeekBar) findViewById(R.id.seekBar_G);
        SeekBar seekBar_Blue = (SeekBar) findViewById(R.id.seekBar_B);
        int[] ValuesArray = new int[8];


        int Color_R = seekBar_Red.getProgress();
        int Color_G = seekBar_Green.getProgress();
        int Color_B = seekBar_Blue.getProgress();

        float Color_R_RGB = (float) Color_R * 2.5f;
        float Color_G_RGB = (float) Color_G * 2.5f;
        float Color_B_RGB = (float) Color_B * 2.5f;



        Color_buttons(Position,(int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB);
        ValuesArray[0] = 5;
        ValuesArray[1] = 'I';
        ValuesArray[2] = 50;
        ValuesArray[3] = 1;
        ValuesArray[4] = Position;
        ValuesArray[5] = (byte) Color_R;
        ValuesArray[6] = (byte) Color_G;
        ValuesArray[7] = (byte) Color_B;
        String message2 = Arrays.toString(ValuesArray) + "\n";

        if (Bluetooth_connected == true){
                byte[] value;
            try {
                //send data to service
                value = message2.getBytes("UTF-8");
                mService.writeRXCharacteristic(value);
                //Update the log with time stamp
                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                listAdapter.add("[" + currentDateTimeString + "] TX: " + Color_R);
                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                edtMessage.setText("");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Update the log with time stamp
            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
            listAdapter.add("[" + currentDateTimeString + "] TX: " + Color_R);
            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
        }
    }
    public void Color_buttons(int button_nr,int Color_R_RGB, int Color_G_RGB, int Color_B_RGB) {
        switch(button_nr) {
            case 0:
                Button_0_0.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 1:
                Button_0_1.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 2:
                Button_0_2.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 3:
                Button_0_3.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 4:
                Button_0_4.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 5:
                Button_1_0.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 6:
                Button_1_1.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 7:
                Button_1_2.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 8:
                Button_1_3.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 9:
                Button_1_4.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 10:
                Button_2_0.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 11:
                Button_2_1.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 12:
                Button_2_2.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 13:
                Button_2_3.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 14:
                Button_2_4.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 15:
                Button_3_0.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 16:
                Button_3_1.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 17:
                Button_3_2.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 18:
                Button_3_3.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 19:
                Button_3_4.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 20:
                Button_4_0.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 21:
                Button_4_1.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 22:
                Button_4_2.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 23:
                Button_4_3.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

            case 24:
                Button_4_4.setBackgroundColor(Color.rgb((int)Color_R_RGB,(int)Color_G_RGB,(int)Color_B_RGB));
                break;

        }
    }
}

