package tech.devatacreative.submission4.Model

import com.google.gson.annotations.SerializedName

data class MatchResponse(

	@field:SerializedName("events")
	val events: List<MatchItem>
)