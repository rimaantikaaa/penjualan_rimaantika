package com.example.penjualan_rimaantika.room

import androidx.room.*

@Dao
interface tbBrg_DAO {

    @Insert
    fun addtbBrg(brg: tb_barang)

    @Update
    fun updatetbBrg(brg: tb_barang)

    @Delete
    fun deletetbBrg(brg: tb_barang)

    @Query("SELECT * FROM tb_barang")
    fun tampilBrg():List<tb_barang>

    @Query("SELECT*FROM tb_barang WHERE id_brg=:id_brg")
    fun barang(id_brg: Int) : List<tb_barang>
}