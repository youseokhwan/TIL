package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(this, 0, it, 0)
        }

        btnStart.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, timePicker.hour)
                set(Calendar.MINUTE, timePicker.minute)
                set(Calendar.SECOND, 0)
            }

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )

            Toast.makeText(this, "${timePicker.hour}시 ${timePicker.minute}분으로 알람이 설정되었습니다.", Toast.LENGTH_SHORT).show()
        }

        btnStop.setOnClickListener {
            alarmManager.cancel(alarmIntent)

            Toast.makeText(this, "알람이 취소되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}