package com.geektech.petproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.geektech.petproject.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFirestore()
    }

    private fun setupFirestore() = with(binding) {
        loginBtn.setOnClickListener {
            val id = IDEt.text.toString().toInt()
            val password = passwordEt.text.toString().trim()

            if (TextUtils.isEmpty(id.toString())) {
                IDEt.error = "Требуется идентификатор карты"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                passwordEt.error = "Пожалуйта введите пароль"
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordEt.error = "Пароль должен содержать из 6 символов"
            }
            saveFireStore(id, password)
        }
    }

    private fun saveFireStore(id: Int, password: String) {
        val db = FirebaseFirestore.getInstance()
        val user : MutableMap<String, Any> = HashMap()
        user["ID"] = id
        user["password"] = password

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "ID number added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "ID failed successfully", Toast.LENGTH_SHORT).show()
            }
    }
}