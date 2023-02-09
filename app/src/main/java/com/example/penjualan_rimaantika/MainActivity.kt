package com.example.penjualan_rimaantika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_rimaantika.room.Constant
import com.example.penjualan_rimaantika.room.db_penjualan
import com.example.penjualan_rimaantika.room.tb_barang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { db_penjualan(this) }
    lateinit var adapterBarang: AdapterBarang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadBarang()

    }
    fun loadBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.tbBrgDao().tampilBrg()
            Log.d("MainActivity","penjualan: $barang")
            withContext(Dispatchers.Main){
                adapterBarang.setData(barang)
            }
        }
    }

    fun setupListener(){
        button.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(id_brg: Int, intentType: Int){
          startActivity(
              Intent(applicationContext, EditActivity::class.java)
                  .putExtra("intent_id", id_brg)
                  .putExtra("intent_type", intentType)
          )
    }
    private fun setupRecyclerView(){
        adapterBarang = AdapterBarang(arrayListOf(), object : AdapterBarang.onAdapterListener{
            override fun onClick(barang: tb_barang) {
                // read detail barang
                intentEdit(barang.id_brg, Constant.TYPE_READ)
            }

            override fun onUpdate(barang: tb_barang) {
                intentEdit(barang.id_brg, Constant.TYPE_UPDATE)
            }

            override fun onDelete(barang: tb_barang) {
                deleteDialog(barang)
            }

        })
        button_create.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterBarang
        }
    }
    private fun deleteDialog(barang: tb_barang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("konfirmasi")
            setMessage("Yakin hapus ${barang.id_brg}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()

            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBrgDao().deletetbBrg(barang)
                    loadBarang()
                }
            }
        }
        alertDialog.show()
    }
}