package com.olamide.cowryconvert.model.rx

class VmResponse(var status: Status, var data: Any?, var error: Throwable?) {

    companion object {
        @JvmStatic
        fun loading(): VmResponse {
            return VmResponse(
                Status.LOADING,
                null,
                null
            )
        }

        @JvmStatic
        fun success(data: Any): VmResponse {
            return VmResponse(
                Status.SUCCESS,
                data,
                null
            )
        }

        @JvmStatic
        fun error(error: Throwable): VmResponse {
            return VmResponse(
                Status.ERROR,
                null,
                error
            )
        }
    }

}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
