package id.owldevsoft.apotekseller.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) {
	data class ResponseUpdate(
		@field:SerializedName("data")
		val data: Int,

		@field:SerializedName("message")
		val message: String,

		@field:SerializedName("status")
		val status: String
	)
}
