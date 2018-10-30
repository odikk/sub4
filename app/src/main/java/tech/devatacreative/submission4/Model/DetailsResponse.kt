package tech.devatacreative.submission4.Model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(

	@field:SerializedName("events")
	val events: List<DetailsItem>
)