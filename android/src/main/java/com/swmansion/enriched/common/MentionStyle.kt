package com.swmansion.enriched.common

data class MentionStyle(
  val color: Int,
  val backgroundColor: Int,
  val underline: Boolean,
  val pressColor: Int? = null,
  val pressBackgroundColor: Int? = null,
  val borderColor: Int = 0,
  val borderRadius: Float = 0f,
  val borderWidth: Float = 0f,
  val fontSize: Float = 0f,
  val fontStyle: Int = 0,
  val fontWeight: Int = 400,
  val letterSpacing: Float = 0f,
  val margin: Float = 0f,
  val marginBottom: Float = margin,
  val marginLeft: Float = margin,
  val marginRight: Float = margin,
  val marginTop: Float = margin,
  val paddingHorizontal: Float = 0f,
  val paddingVertical: Float = 0f,
)
