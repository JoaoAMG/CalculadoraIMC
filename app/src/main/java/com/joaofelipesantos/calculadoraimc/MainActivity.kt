package com.joaofelipesantos.calculadoraimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ValueAnimator
import android.icu.text.IDNA.Info
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.text.DecimalFormat
import android.view.inputmethod.InputMethodManager
import android.content.Context



class MainActivity : AppCompatActivity() {

    private lateinit var Altura: EditText
    private lateinit var Peso: EditText
    private lateinit var Resultado: TextView
    private lateinit var Info: TextView
    private lateinit var Tinfo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Altura = findViewById(R.id.editTextAltura)
        Peso = findViewById(R.id.editTextPeso)
        Resultado = findViewById(R.id.TextResultado)
        Info = findViewById(R.id.TextInfo)
        Tinfo = findViewById(R.id.titleInfo)
    }

    fun Calcular(view: View) {
        val Alt = Altura.text.toString().toDoubleOrNull() ?: 0.0
        val Pes = Peso.text.toString().toDoubleOrNull() ?: 0.0


        val imcCal = Pes / (Alt * Alt) * 10000
        val df = DecimalFormat("#.##") // Define o formato para 4 dígitos decimais
        val imcFormatado = df.format(imcCal)
        Resultado.text = "$imcFormatado"


        val Tinfo = when {
            imcCal < 18.5 -> "Abaixo do normal"
            imcCal < 24.9 -> "Normal"
            imcCal < 29.9 -> "Sobrepeso"
            imcCal  < 34.9 -> "Obesidade grau I"
            imcCal  < 39.9 -> "Obesidade grau II"
            else -> "Obesidade grau III"
        }
        this.Tinfo.text = Tinfo


        val Info = when {
        imcCal < 18.5 -> "Procure um médico. Algumas pessoas têm um baixo peso por características do seu organismo e tudo bem. Outras podem estar enfrentando problemas, como a desnutrição. É preciso saber qual é o caso."
        imcCal < 24.9 -> "Que bom que você está com o peso normal! E o melhor jeito de continuar assim é mantendo um estilo de vida ativo e uma alimentação equilibrada."
        imcCal < 29.9 -> "Ele é, na verdade, uma pré-obesidade e muitas pessoas nessa faixa já apresentam doenças associadas, como diabetes e hipertensão. Importante rever hábitos e buscar ajuda antes de, por uma série de fatores, entrar na faixa da obesidade pra valer."
        imcCal  < 34.9 -> "Sinal de alerta! Chegou na hora de se cuidar, mesmo que seus exames sejam normais. Vamos dar início a mudanças hoje! Cuide de sua alimentação. Você precisa iniciar um acompanhamento com nutricionista e/ou endocrinologista."
        imcCal  < 39.9 -> "Mesmo que seus exames aparentem estar normais, é hora de se cuidar, iniciando mudanças no estilo de vida com o acompanhamento próximo de profissionais de saúde."
        else -> "Aqui o sinal é vermelho, com forte probabilidade de já existirem doenças muito graves associadas. O tratamento deve ser ainda mais urgente."
       }
        this.Info.text = Info

        animateCountingResult(imcCal)


            // ... (código existente)

            // Fechar o teclado virtual
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)



    }

    private fun animateCountingResult(finalValue: Double) {
        val initialValue = 0.0

        // Crie um ValueAnimator para animar a propriedade "text" do TextView
        val animator = ValueAnimator.ofFloat(initialValue.toFloat(), finalValue.toFloat())

        // Defina a duração da animação (em milissegundos)
        animator.duration = 1000

        // Adicione um ouvinte para atualizar o texto durante a animação
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            Resultado.text = String.format("%.2f", animatedValue)
        }

        // Inicie a animação
        animator.start()
    }
}
