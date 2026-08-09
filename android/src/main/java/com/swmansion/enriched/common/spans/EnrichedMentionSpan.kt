package com.swmansion.enriched.common.spans

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.text.style.ReplacementSpan
import android.view.View
import com.swmansion.enriched.common.EnrichedStyle
import com.swmansion.enriched.common.MentionStyle
import com.swmansion.enriched.common.spans.interfaces.EnrichedInlineSpan
import kotlin.math.ceil

open class EnrichedMentionSpan(
  private val text: String,
  private val indicator: String,
  private val attributes: Map<String, String>,
  private val enrichedStyle: EnrichedStyle,
) : ReplacementSpan(),
  EnrichedInlineSpan {
  open fun onClick(view: View) {
    // Mentions inside the input are dispatched by its touch handler.
  }

  protected open fun textColor(style: MentionStyle): Int = style.color

  protected open fun backgroundColor(style: MentionStyle): Int = style.backgroundColor

  private val hasClockIcon: Boolean
    get() = attributes["icon"] == "clock3"

  private fun styledPaint(source: Paint): Paint =
    Paint(source).apply {
      val style = enrichedStyle.mentionsStyle[indicator] ?: return@apply
      color = textColor(style)
      isUnderlineText = style.underline
      if (style.fontSize > 0f) textSize = style.fontSize
      letterSpacing = if (textSize > 0f) style.letterSpacing / textSize else 0f
      val typefaceStyle =
        (if (style.fontWeight >= 500) Typeface.BOLD else Typeface.NORMAL) or
          (if (style.fontStyle == Typeface.ITALIC) Typeface.ITALIC else Typeface.NORMAL)
      typeface = Typeface.create(typeface, typefaceStyle)
    }

  private fun contentWidth(paint: Paint): Float {
    val iconWidth = if (hasClockIcon) paint.textSize + paint.textSize / 3f else 0f
    return iconWidth + paint.measureText(text)
  }

  override fun getSize(
    paint: Paint,
    text: CharSequence,
    start: Int,
    end: Int,
    fontMetrics: Paint.FontMetricsInt?,
  ): Int {
    val style = enrichedStyle.mentionsStyle[indicator]
    val mentionPaint = styledPaint(paint)
    if (fontMetrics != null) {
      val metrics = mentionPaint.fontMetricsInt
      val topSpace = ceil((style?.paddingVertical ?: 0f) + (style?.marginTop ?: 0f)).toInt()
      val bottomSpace = ceil((style?.paddingVertical ?: 0f) + (style?.marginBottom ?: 0f)).toInt()
      fontMetrics.top = metrics.top - topSpace
      fontMetrics.ascent = metrics.ascent - topSpace
      fontMetrics.descent = metrics.descent + bottomSpace
      fontMetrics.bottom = metrics.bottom + bottomSpace
    }
    val horizontalSpace =
      2f * (style?.paddingHorizontal ?: 0f) +
        (style?.marginLeft ?: 0f) +
        (style?.marginRight ?: 0f)
    return ceil(contentWidth(mentionPaint) + horizontalSpace).toInt()
  }

  override fun draw(
    canvas: Canvas,
    text: CharSequence,
    start: Int,
    end: Int,
    x: Float,
    top: Int,
    y: Int,
    bottom: Int,
    paint: Paint,
  ) {
    val style = enrichedStyle.mentionsStyle[indicator] ?: return
    val mentionPaint = styledPaint(paint)
    val contentWidth = contentWidth(mentionPaint)
    val left = x + style.marginLeft
    val right = left + style.paddingHorizontal * 2f + contentWidth
    val metrics = mentionPaint.fontMetrics
    val rect =
      RectF(
        left,
        y + metrics.ascent - style.paddingVertical,
        right,
        y + metrics.descent + style.paddingVertical,
      )
    val boxPaint =
      Paint(paint).apply {
        color = backgroundColor(style)
        this.style = Paint.Style.FILL
      }
    canvas.drawRoundRect(rect, style.borderRadius, style.borderRadius, boxPaint)

    if (style.borderWidth > 0f) {
      val inset = style.borderWidth / 2f
      rect.inset(inset, inset)
      boxPaint.color = style.borderColor
      boxPaint.strokeWidth = style.borderWidth
      boxPaint.style = Paint.Style.STROKE
      val radius = (style.borderRadius - inset).coerceAtLeast(0f)
      canvas.drawRoundRect(rect, radius, radius, boxPaint)
    }

    var contentX = left + style.paddingHorizontal
    if (hasClockIcon) {
      val iconSize = mentionPaint.textSize
      val iconTop = y + (metrics.ascent + metrics.descent - iconSize) / 2f
      val scale = iconSize / 24f
      val iconPaint =
        Paint(mentionPaint).apply {
          this.style = Paint.Style.STROKE
          strokeWidth = 2f * scale
          strokeCap = Paint.Cap.ROUND
          strokeJoin = Paint.Join.ROUND
        }
      canvas.drawCircle(contentX + 12f * scale, iconTop + 12f * scale, 10f * scale, iconPaint)
      val hands =
        Path().apply {
          moveTo(contentX + 12f * scale, iconTop + 6f * scale)
          lineTo(contentX + 12f * scale, iconTop + 12f * scale)
          lineTo(contentX + 16f * scale, iconTop + 12f * scale)
        }
      canvas.drawPath(hands, iconPaint)
      contentX += iconSize + iconSize / 3f
    }
    canvas.drawText(this.text, contentX, y.toFloat(), mentionPaint)
  }

  fun getAttributes(): Map<String, String> = attributes

  fun getText(): String = text

  fun getIndicator(): String = indicator
}
