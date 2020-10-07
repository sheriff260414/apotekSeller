package id.owldevsoft.apotekseller.utils

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.Toast
import id.owldevsoft.apotekseller.R
import java.util.*

class BubbleService : Service() {

    private var view: View? = null
    private var windowManager: WindowManager? = null
//    private var imageView: ImageView? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        view = LayoutInflater.from(this).inflate(R.layout.item_bubble_wa, null)

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        var layout_type = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layout_type = WindowManager.LayoutParams.TYPE_PHONE
        }
        val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layout_type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100

        view?.isClickable = true
        windowManager?.addView(view, params)

        view?.setOnTouchListener(object : View.OnTouchListener {

            private var initialX: Int = 0
            private var initialY: Int = 0
            private var lastAction: Int = 0
            private var touchX: Float = 0F
            private var touchY: Float = 0F
            private val MAX_CLICK_DURATION = 200
            private var startClickTime: Long = 0

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event != null) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        initialX = params.x
                        initialY = params.y

                        touchX = event.rawX
                        touchY = event.rawY
                        lastAction = event.action

                        lastAction = event.action
                        startClickTime = Calendar.getInstance().timeInMillis;
                        return true
                    }
                    if (event.action == MotionEvent.ACTION_UP) {
                        val clickDuration =
                            Calendar.getInstance().timeInMillis - startClickTime
                        if (clickDuration < MAX_CLICK_DURATION) {
                            try {
                                val uri =
                                    Uri.parse("https://api.whatsapp.com/send?phone=62" + "082146749494" + "&text=Hallo")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                intent.setPackage("com.whatsapp")
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent)
                                stopSelf()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    baseContext,
                                    "Mohon Install Aplikasi Whatsapp Terlebih Dahulu",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    if (event.action == MotionEvent.ACTION_MOVE) {
                        params.x = (initialX + (event.rawX - touchX)).toInt()
                        params.y = (initialY + (event.rawY - touchY)).toInt()

                        windowManager?.updateViewLayout(view, params)
                        lastAction = event.action
                        return true

                    }

                }

                return false
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager!!.removeView(view)
    }

}