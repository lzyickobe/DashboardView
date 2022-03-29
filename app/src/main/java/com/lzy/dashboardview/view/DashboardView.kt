package com.lzy.dashboardview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin


/**
 * 画仪表盘
 */
val RADIUS = 100f.px
const val OPEN_ANGLE = 120
val DASH_WIDTH = 3f.px
val DASH_HEIGHT = 10f.px
val MARK_LENGTH = 100f.px
class DashboardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcPath = Path() //弧形
    private val dash = Path() //小刻度的矩形
    private var markVaule = 0.0f //刻度值
    lateinit var pathDashPathEffect: PathDashPathEffect
    private val circleAnimator : ObjectAnimator = ObjectAnimator.ofInt(this, "intMark", getIntMark(), getIntMark()+20)
    private val smoothOneAnimator : ObjectAnimator = ObjectAnimator.ofFloat(this, "mark", getIntMark().toFloat(), getIntMark().toFloat()+1f)
    private val smoothCircleAnimator : ObjectAnimator = ObjectAnimator.ofFloat(this, "mark", getMark(), getMark()+20)
    init {
        paint.strokeWidth = 3f.px

        paint.style = Paint.Style.STROKE
        dash.addRect(0f,0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CW)
    }
    override fun onDraw(canvas: Canvas) {
        //1 画弧线
        paint.color = Color.RED
        canvas.drawPath(arcPath,paint)
        //2 画刻度
        paint.pathEffect = pathDashPathEffect
        paint.color = Color.BLUE
        canvas.drawArc(width/2f - 150f.px,height/2f -150f.px, width/2f+150f.px, height/2f+150f.px,150f, 240f,false,paint)
        paint.pathEffect = null
        //3 画指针
        paint.color = Color.DKGRAY
        canvas.drawLine(width/2f, height/2f, width/2f + cos(markToRadians(markVaule)).toFloat()*MARK_LENGTH, height/2f + sin(markToRadians(markVaule)).toFloat()*MARK_LENGTH,paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        arcPath.reset()
        arcPath.addArc(width/2f - 150f.px,height/2f -150f.px, width/2f+150f.px, height/2f+150f.px,150f, 240f)
        //找到弧形path的长度
        val pathMeasure = PathMeasure(arcPath, false)
        //以当前的path路径来画一个个的dash小长方形
        pathDashPathEffect = PathDashPathEffect(dash, (pathMeasure.length-DASH_WIDTH)/20f,0f, PathDashPathEffect.Style.ROTATE)

    }

    //找到刻度所对应的角度值
    private fun markToRadians(mark :Float) =
        Math.toRadians((90 + OPEN_ANGLE / 2f + (360- OPEN_ANGLE) / 20f * mark).toDouble())

    fun setMark(mark: Float) {
        if (mark > 20f) {
            markVaule = mark.toInt() % 20 + (mark - mark.toInt())
        } else {
            markVaule = mark
        }
        invalidate()
    }

    fun getMark() : Float {
        return markVaule
    }

    fun setIntMark(mark : Int) {
        if (mark > 20) {
            markVaule = (mark % 20).toFloat()
        } else {
            markVaule = mark.toFloat()
        }
        invalidate()
    }

    fun getIntMark() : Int {
        return markVaule.toInt()
    }

    /**
     * 指针向前移动1格
     */
    fun addMarkOne() {
        circleAnimator.cancel()
        smoothOneAnimator.cancel()
        smoothCircleAnimator.cancel()
        setIntMark(getIntMark()+1)
        invalidate()
    }

    /**
     * 指针向前移动20格，1格1格移动
     */
    fun addMarkCircle() {
        smoothOneAnimator.cancel()
        smoothCircleAnimator.cancel()
        setMark(0f)
        circleAnimator.duration = 1000*20
        circleAnimator.interpolator = LinearInterpolator()
        circleAnimator.start()
    }

    /**
     * 指针平滑向前移动1格
     */
    fun smoothAddMarkOne() {
        circleAnimator.cancel()
        smoothCircleAnimator.cancel()
        smoothOneAnimator.duration = 1000
        smoothOneAnimator.interpolator = LinearInterpolator()
        smoothOneAnimator.start()
    }

    /**
     * 指针平滑向前移动20格
     */
    fun smoothAddMarkCircle() {
        setMark(0f)
        circleAnimator.cancel()
        smoothOneAnimator.cancel()
        smoothCircleAnimator.duration = 1000*20
        smoothCircleAnimator.interpolator = LinearInterpolator()
        smoothCircleAnimator.start()
    }


}