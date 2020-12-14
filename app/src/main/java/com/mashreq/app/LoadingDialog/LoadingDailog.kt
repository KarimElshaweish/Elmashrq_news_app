package com.mashreq.app.LoadingDialog

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower


class LoadingDailog (val _ctx:Context){
    lateinit var dialog:ACProgressFlower
     fun showDialog(){
          dialog = ACProgressFlower.Builder(_ctx)
                 .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                 .themeColor(Color.WHITE)
                 .text("Loading")
                 .fadeColor(Color.DKGRAY).build()
         dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }

}