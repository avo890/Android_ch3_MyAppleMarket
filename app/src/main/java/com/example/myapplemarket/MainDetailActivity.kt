package com.example.myapplemarket

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.myapplemarket.databinding.ActivityMainDetailBinding
import com.google.android.material.snackbar.Snackbar

class MainDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainDetailBinding
    private var isLiked = false

    //메인 액티비티에서 Parcelize를 사용해 putExtra로 넘겨준 해당 포지션 데이터 자체를 받아오기
    private val productData: ProductItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("productData", ProductItem::class.java)
        } else {
            intent.getParcelableExtra<ProductItem>("productData")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액티비티메인디테일 xml파일에 getParcelableExtra로 가져온 각 값 바인딩하기
        productData?.ivProduct?.let { binding.ivDetailProduct.setImageResource(it) }
        binding.tvDetailSeller.text = productData?.tvSeller
        binding.tvDetailLoca.text = productData?.tvLoca
        binding.tvDetailTitle.text = productData?.tvTitle
        binding.tvDetailDescription.text = productData?.tvDescription
        binding.tvDetailPrice.text = productData?.tvPrice

          //+`productData?.isLiked` 값이 `true`인 경우인지 비교하여 isLiked에 할당.
        isLiked = productData?.isLiked == true
        binding.ivDetailLiked.setImageResource(
            if(isLiked) { R.drawable.item_heart_filled} else {R.drawable.item_heart}
        )

        //뒤로가기 아이콘 눌렀을 때
        binding.ivBack.setOnClickListener {
            exit()
        }

        //그냥 핸드폰 자체에서 뒤로가기 눌렀을 때 콜백
        this.onBackPressedDispatcher.addCallback(this, callback)


        //좋아요 아이콘 클릭시 아이콘 전환
        binding.ivDetailLiked.setOnClickListener {
            if (!isLiked) {
                binding.ivDetailLiked.setImageResource(R.drawable.item_heart_filled)
                Snackbar.make(it, "관심 목록에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
                isLiked = true
            } else {
                binding.ivDetailLiked.setImageResource(R.drawable.item_heart)
                isLiked = false
            }
        }
    }

    //종료되었을 때 다시 메인액티비티로 좋아요에 대한 값 전송
    private fun exit() {
        val dataPosition = intent.getIntExtra("dataPosition", 0)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("dataPosition", dataPosition)
        intent.putExtra("isLiked", isLiked)
        setResult(RESULT_OK,intent)
        finish()
    }

    //핸드폰 자체 뒤로가기 눌렀을때 발생할 이벤트 등록
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exit()
        }
    }

}


