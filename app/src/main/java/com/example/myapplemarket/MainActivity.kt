package com.example.myapplemarket

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplemarket.databinding.ActivityMainBinding
import com.example.myapplemarket.databinding.LayoutItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemBinding: LayoutItemBinding

    private lateinit var resultLaunchere: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //데이터리스트
        val dataList = mutableListOf<ProductItem>()
        dataList.add(ProductItem(R.drawable.sample1, "1,000원", "산지 한달된 선풍기 팝니다", "이사가서 필요가 없어졌어요 급하게 내놓습니다", "대현동", "서울 서대문구 창천동", 13, 25,false))
        dataList.add(ProductItem(R.drawable.sample2, "20,000원", "김치냉장고", "이사로인해 내놔요", "안마담", "인천 계양구 귤현동", 8, 28,false))
        dataList.add(ProductItem(R.drawable.sample3, "10,000원", "샤넬 카드지갑", "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다", "코코유", "수성구 범어동", 23, 5,false))
        dataList.add(ProductItem(R.drawable.sample4, "10,000원", "금고", "금고\n떼서 가져가야함\n대우월드마크센텀\n미국이주관계로 싸게 팝니다", "Nicole", "해운대구 우제2동", 14, 17,false))
        dataList.add(ProductItem(R.drawable.sample5, "150,000원", "갤럭시Z플립3 팝니다", "갤럭시 Z플립3 그린 팝니다\n항시 케이스 씌워서 썻고 필름 한장챙겨드립니다\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!", "절명", "연제구 연산제8동", 22, 9,false))
        dataList.add(ProductItem(R.drawable.sample6, "50,000원", "프라다 복조리백", "까임 오염없고 상태 깨끗합니다\n정품여부모름", "미니멀하게", "수원시 영통구 원천동", 25, 16,false))
        dataList.add(ProductItem(R.drawable.sample7, "150,000원", "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10. 저희 집안이 쓰는 별장입니다.", "굿리치", "남구 옥동", 142, 54,false))
        dataList.add(ProductItem(R.drawable.sample8, "180,000원", "샤넬 탑핸들 가방", "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 !\n \n색상 : 블랙\n사이즈 : 25.5cm * 17.5cm * 8cm\n구성 : 본품더스트\n\n급하게 돈이 필요해서 팝니다 ㅠ ㅠ", "난쉽", "동래구 온천제2동", 31, 7,false))
        dataList.add(ProductItem(R.drawable.sample9, "30,000원", "4행정 엔진분무기 판매합니다.", "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.\n", "알뜰한", "원주시 명륜2동", 7, 28,false))
        dataList.add(ProductItem(R.drawable.sample10, "190,000원", "셀린느 버킷 가방", "22년 신세계 대전 구매입니당\n 셀린느 버킷백\n구매해서 몇번사용했어요\n까짐 스크래치 없습니다.\n타지역에서 보내는거라 택배로 진행합니당!", "똑태현", "중구 동화동", 40, 6,false))

        //리사이클러뷰만들어주기
        val adapter = ProductAdapter(dataList)
        binding.productRecycle.adapter = adapter
        binding.productRecycle.layoutManager = LinearLayoutManager(this)

        //뒤로가기버튼 콜백
        this.onBackPressedDispatcher.addCallback(this, callback)

        //알림버튼누르면 알림생성
        binding.btnNotification.setOnClickListener {
            notification()
        }

        //좋아요버튼 쌍방향통신 인텐트 콜백
        resultLaunchere = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val dataPosition = result.data?.getIntExtra("dataPosition",0) as Int
                val isLiked = result.data?.getBooleanExtra("isLiked",false) as Boolean
                if(isLiked) {
                    dataList[dataPosition].isLiked = true
                    dataList[dataPosition].tvLiked += 1
                } else {
                    dataList[dataPosition].isLiked = false
                    dataList[dataPosition].tvLiked -= 1
                }

                adapter.notifyItemChanged(dataPosition)
            }
        }


        //리사이클러뷰 아이템 눌러서 상세페이지로 넘어가기
        adapter.productClick = object : ProductAdapter.ProductClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, MainDetailActivity::class.java)
                intent.putExtra("dataPosition",position)
                intent.putExtra("productData", dataList[position])
                resultLaunchere.launch(intent)
            }
        }

        //리사이클러뷰 아이템 길게 눌러서 알림으로 확인 후 목록 삭제하기
        adapter.productLongClick = object : ProductAdapter.ProductLongClick {
            override fun onLongClick(view: View, position: Int) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("상품 삭제")
                builder.setIcon(R.drawable.item_chat)
                builder.setMessage("상품을 정말로 삭제하시겠습니까?")

                val listener = DialogInterface.OnClickListener { _, p1 ->
                    if (p1 == DialogInterface.BUTTON_POSITIVE) {
                        dataList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position,dataList.size)

                    }
                }
                builder.setPositiveButton("확인", listener)
                builder.setNegativeButton("취소", null)
                builder.show()
            }

        }

        //플로팅 버튼 눌러서 상단으로 이동
        binding.btnFloating.setOnClickListener {
            binding.productRecycle.smoothScrollToPosition(0)
        }

        //플로팅버튼 스크롤상태에 따라 fadein/fadeout효과를 통해 표시하기.
        binding.productRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(-1)) {
                    if (binding.btnFloating.visibility != View.GONE) {
                        binding.btnFloating.apply {
                            visibility = View.GONE
                            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out))
                        }
                    }

                } else {
                    if (binding.btnFloating.visibility != View.VISIBLE) {
                        binding.btnFloating.apply {
                            visibility = View.VISIBLE
                            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in))
                        }
                    }

                }
            }
        })

    }


    //뒤로가기 버튼 누르면 알림 띄우기
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("종료")
            builder.setIcon(R.drawable.item_chat)
            builder.setMessage("정말 종료하시겠습니까?")

            val listener = DialogInterface.OnClickListener { _, p1 ->
                if (p1 == DialogInterface.BUTTON_POSITIVE) {
                    finish()
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)
            builder.show()
        }

    }

    //알림채널을 통해 알림 만드는 함수
    private fun notification() {

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "My Channel One Description"
            setShowBadge(true)
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            setSound(uri, audioAttributes)
            enableVibration(true)
        }

        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(this, channelId)

        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
        }

        manager.notify(11, builder.build())

    }

}