package com.ruangujung.fuzzytsukamoto

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var MKBaru : Double = 0.0
    var MKSedang : Double = 0.0
    var MKLama : Double = 0.0
    var JGSedikit : Double = 0.0
    var JGBanyak : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hitungBonus.setOnClickListener {
            if (masaKerja.text.isNotEmpty()&&jumlahGaji.text.isNotEmpty()){
                MKBaru = 0.0
                MKSedang = 0.0
                MKLama = 0.0
                JGSedikit = 0.0
                JGBanyak = 0.0
                hitung(masaKerja.text.toString().toInt(),jumlahGaji.text.toString().toInt())
            }else{
                Toast.makeText(this,"Isi semua data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun hitung(MK: Int, JG: Int) {
        when {
            MK<=5 -> {
                MKBaru = ExpressionBuilder("(5-x)/(5-2)")
                    .variable("x")
                    .build()
                    .setVariable("x",MK.toDouble())
                    .evaluate()
                MKSedang = ExpressionBuilder("(x-3)/(5-3)")
                    .variable("x")
                    .build()
                    .setVariable("x",MK.toDouble())
                    .evaluate()
            }
            else -> {
                MKSedang = ExpressionBuilder("(7-x)/(7-5)")
                    .variable("x")
                    .build()
                    .setVariable("x",MK.toDouble())
                    .evaluate()
                MKLama = ExpressionBuilder("(x-5)/(8-5)")
                    .variable("x")
                    .build()
                    .setVariable("x",MK.toDouble())
                    .evaluate()
            }
        }
        JGSedikit = ExpressionBuilder("(4000000-x)/(4000000-2000000)")
            .variable("x")
            .build()
            .setVariable("x",JG.toDouble())
            .evaluate()
        JGBanyak = ExpressionBuilder("(x-3000000)/(5000000-3000000)")
            .variable("x")
            .build()
            .setVariable("x",JG.toDouble())
            .evaluate()
        MKBaru      = if (MKBaru<0.0)0.0 else if (MKBaru>1.0) 1.0 else DecimalFormat("#.000").format(MKBaru).toDouble()
        MKSedang    = if (MKSedang<0.0)0.0 else if (MKSedang>1.0) 1.0 else DecimalFormat("#.000").format(MKSedang).toDouble()
        MKLama      = if (MKLama<0.0)0.0 else if (MKLama>1.0) 1.0 else DecimalFormat("#.000").format(MKLama).toDouble()
        JGSedikit   = if (JGSedikit<0.0)0.0 else if (JGSedikit>1.0) 1.0 else DecimalFormat("#.000").format(JGSedikit).toDouble()
        JGBanyak    = if (JGBanyak<0.0)0.0 else if (JGBanyak>1.0) 1.0 else DecimalFormat("#.000").format(JGBanyak).toDouble()
        hasil.text = "MKBaru = $MKBaru\n" +
                "MKSedang = $MKSedang\n" +
                "MKLama = $MKLama\n" +
                "JGSedikit = $JGSedikit\n" +
                "JGBanyak = $JGBanyak\n"
        /*
                 MK     JG      H
        Rule 1 = baru   sedikit sedikit
        Rule 2 = baru   banyak  sedikit
        Rule 3 = sedang sedikit sedikit
        Rule 4 = sedang banyak  banyak
        Rule 5 = lama   sedikit banyak
        Rule 6 = lama   banyak  banyak
         */
        val a1 = if (MKBaru<=JGSedikit) MKBaru else JGSedikit
        val a2 = if (MKBaru<=JGBanyak) MKBaru else JGBanyak
        val a3 = if (MKSedang<=JGSedikit) MKSedang else JGSedikit
        val a4 = if (MKSedang<=JGBanyak) MKSedang else JGBanyak
        val a5 = if (MKLama<=JGSedikit) MKLama else JGSedikit
        val a6 = if (MKLama<=JGBanyak) MKLama else JGBanyak
        val z1 = 600000-(a1*300000)
        val z2 = ExpressionBuilder("600000-(x*300000)")
            .variable("x")
            .build()
            .setVariable("x",a2)
            .evaluate()
        val z3 = ExpressionBuilder("600000-(x*300000)")
            .variable("x")
            .build()
            .setVariable("x",a3)
            .evaluate()
        val z4 = ExpressionBuilder("300000+(x*300000)")
            .variable("x")
            .build()
            .setVariable("x",a4)
            .evaluate()
        val z5 = ExpressionBuilder("300000+(x*300000)")
            .variable("x")
            .build()
            .setVariable("x",a5)
            .evaluate()
        val z6 = ExpressionBuilder("300000+(x*300000)")
            .variable("x")
            .build()
            .setVariable("x",a6)
            .evaluate()
        val z = ExpressionBuilder("((a1*z1)+(a2*z2)+(a3*z3)+(a4*z4)+(a5*z5)+(a6*z6))/(a1+a2+a3+a4+a5+a6)")
            .variables("a1","a2","a3","a4","a5","a6","z1","z2","z3","z4","z5","z6")
            .build()
            .setVariable("a1",a1)
            .setVariable("a2",a2)
            .setVariable("a3",a3)
            .setVariable("a4",a4)
            .setVariable("a5",a5)
            .setVariable("a6",a6)
            .setVariable("z1",z1)
            .setVariable("z2",z2)
            .setVariable("z3",z3)
            .setVariable("z4",z4)
            .setVariable("z5",z5)
            .setVariable("z6",z6)
            .evaluate()
        hasil.text = "MKBaru = $MKBaru\n" +
                "MKSedang = $MKSedang\n" +
                "MKLama = $MKLama\n" +
                "JGSedikit = $JGSedikit\n" +
                "JGBanyak = $JGBanyak\n"+
                "a1 = $a1\n" +
                "z1 = $z1\n" +
                "a2 = $a2\n" +
                "z2 = $z2\n" +
                "a3 = $a3\n" +
                "z3 = $z3\n" +
                "a4 = $a4\n" +
                "z4 = $z4\n" +
                "a5 = $a5\n" +
                "z5 = $z5\n" +
                "a6 = $a6\n" +
                "z6 = $z6\n" +
                "z = ${DecimalFormat("#.000").format(z).toDouble()}\n"
    }
}
