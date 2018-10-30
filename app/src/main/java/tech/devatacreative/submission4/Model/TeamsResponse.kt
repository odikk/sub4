package tech.devatacreative.submission4.Model

import com.google.gson.annotations.SerializedName

data class TeamsResponse(

	@field:SerializedName("teams")
	val teams: List<TeamsItem?>? = null
)