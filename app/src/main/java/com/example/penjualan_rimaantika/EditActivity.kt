package com.example.penjualan_rimaantika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_rimaantika.room.Constant
import com.example.penjualan_rimaantika.room.db_penjualan
import com.example.penjualan_rimaantika.room.tbBrg_DAO
import com.example.penjualan_rimaantika.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.adapter_barang.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    private var brgId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
        brgId = intent.getIntExtra("intent_id",0)
        Toast.makeText(this, brgId.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                btnSave.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                tampilBarang()

            }
            Constant.TYPE_UPDATE -> {
                btnSave.visibility = View.GONE
                tampilBarang()

            }
        }
    }

    fun setupListener(){
        btnSave.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
               db.tbBrgDao().addtbBrg(
                   tb_barang(et_id.text.toString().toInt(),et_nmBrg.text.toString(),
                             et_hrgBrg.text.toString().toInt(),
                             et_stok.text.toString())
               )
                finish()
            }
        }
        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBrgDao().updatetbBrg(
                    tb_barang(et_id.text.toString().toInt(),et_nmBrg.text.toString(),
                        et_hrgBrg.text.toString().toInt(),
                        et_stok.text.toString())
                )
                finish()
            }
        }
    }
    fun tampilBarang(){
        brgId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val brg = db.tbBrgDao().barang(brgId)[0]
            val id : String = brg.id_brg.toString()
            val stok : String = brg.stok

            et_id.setText(id)
            et_nmBrg.setText(brg.nm_brg)
            et_hrgBrg.setText(brg.hrg_brg)
            et_stok.setText(stok)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}