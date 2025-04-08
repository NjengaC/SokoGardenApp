package com.modcom.yoghurts
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams
import com.modcom.yoghurts.ApiHelper
import com.modcom.yoghurts.R
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
//        val desc = intent.getStringExtra("product_description")
//        val photo = intent.getStringExtra("product_photo")

        val txtName: TextView = findViewById(R.id.txtProductName)
        val txtCost: TextView = findViewById(R.id.txtProductCost)
//        val txtDesc: TextView = findViewById(R.id.txtProductDesc)
//        val imgProduct: ImageView = findViewById(R.id.imgProduct)

        txtName.text = name
        txtCost.text = "Ksh $cost"
//        txtDesc.text = desc


//        val imageUrl = "https://modcom2.pythonanywhere.com/static/images/$photo"
//        Glide.with(this)
//            .load(imageUrl)
//            .placeholder(R.drawable.ic_launcher_background)
//            .into(imgProduct)

        val btnPay: Button = findViewById(R.id.pay)
        val edtPhone: EditText = findViewById(R.id.phone)
        btnPay.setOnClickListener {
            val api = "https://modcom2.pythonanywhere.com/api/mpesa_payment"
            val phone = edtPhone.text.toString().trim()
            val amount = cost.toString() // assuming cost is passed via Intent

            val params = RequestParams()
            params.put("amount", amount)
            params.put("phone", phone)

            val helper = ApiHelper(applicationContext)
            helper.post(api, params)
        }
    }
    }
