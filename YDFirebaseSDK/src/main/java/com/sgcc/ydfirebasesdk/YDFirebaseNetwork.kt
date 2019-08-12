package com.sgcc.ydfirebasesdk

import android.text.TextUtils
import com.google.android.gms.common.util.Base64Utils
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.util.*
import kotlin.collections.HashMap
import java.io.OutputStream


class YDFirebaseNetwork {

    companion object {

        val HTTP_KEY = "75e579d16da7b53eb3a8eb93820adf7e"

        /**
         * 上传注册推送回调Token
         *
         * @pram baseURL 上传token的URL
         * @pram action 上传Token的action
         * @pram 用户的Token
         * @pram firebaseToken 注册推送时回调Token
         * */
        fun uploadFirebaseToken(baseURL: String, action: String, userToke: String, firebaseToken: String) {

            Thread(Runnable {

                var connection: HttpURLConnection? = null
                var reader: BufferedReader? = null
                try {

                    val data = HashMap<String, Any>()
                    data.put("firebase_token", firebaseToken)
                    val params = createHttpParams(action, data)

                    val url = URL(baseURL)//新建URL
                    connection = url.openConnection() as HttpURLConnection//发起网络请求
                    connection.setRequestMethod("POST")//请求方式
                    connection.setConnectTimeout(30000)//连接最大时间
                    connection.setReadTimeout(30000)//读取最大时间
                    connection.addRequestProperty("token", userToke)
                    connection.addRequestProperty("Content-Type", "application/Json; charset=UTF-8")


                    //获取输出流
                    val outputStream: OutputStream = connection.getOutputStream()
                    //输出流里写入POST参数
                    outputStream.write(hexStringToBytes(params))

                    outputStream.flush()
                    outputStream.close()

                    val responseCode = connection.getResponseCode()

                    val br = BufferedReader(InputStreamReader(connection.getInputStream(), "UTF-8"))
                    val result = br.readLine()

                    if (responseCode == 200 && result != null) {

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (reader != null) {
                        try {
                            reader.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                    if (connection != null) {
                        connection.disconnect()
                    }
                }

            }).start()

        }



        fun createHttpParams(action: String, data: Map<String, Any>): String {
            val map = HashMap<String, String>()
            val dataJson: String = this.mapToJson(data)
            val dataByte = hexStringToBytes(dataJson)
            var dataStr = Base64Utils.encode(dataByte)
            map["data"] = dataStr

            if (TextUtils.isEmpty(dataStr)) {
                dataStr = "None"//python 后台空必须传这个
            }
            val timestamp = getTimestamp()
            val key = HTTP_KEY
            val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")
            map["nonce"] = uuid
            map["signature"] = md5(dataStr + timestamp + key + uuid)
            map["timestamp"] = timestamp
            map["action"] = action

            map.toString()

            return map.toString()
        }

        fun getTimestamp(): String {
            val time = System.currentTimeMillis() / 1000//获取系统时间的10位的时间戳
            return time.toString()
        }

        fun md5(string: String): String {
            val hash: ByteArray
            try {
                hash = MessageDigest.getInstance("MD5").digest(
                    string.toByteArray(charset("UTF-8"))
                )
                val hex = StringBuilder(hash.size * 2)
                for (b in hash) {
                    if (b < 0x10)
                        hex.append("0")
                    hex.append(Integer.toHexString(b.toInt()))
                }
                return hex.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                return string
            }

        }


        fun createTokenHeaderParams(token: String): HashMap<String, String> {
            val headerParams = HashMap<String, String>()
            headerParams["token"] = token
            return headerParams
        }

        /**
         * 将Map转化为Json
         *
         * @param map
         * @return String
         */
        fun mapToJson(map: Map<String, Any>): String {
            val gson = Gson()
            return gson.toJson(map)
        }

        /**
         * 十六进制String转换成Byte[]
         * @param hexString the hex string
         * *
         * @return byte[]
         */
        fun hexStringToBytes(hexString: String?): ByteArray? {
            var hexString = hexString
            if (hexString == null || hexString == "") {
                return null
            }
            hexString = hexString.toUpperCase()
            val length = hexString.length / 2
            val hexChars = hexString.toCharArray()
            val d = ByteArray(length)
            for (i in 0..length - 1) {
                val pos = i * 2
                d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
            }
            return d
        }

        /**
         * Convert char to byte
         * @param c char
         * *
         * @return byte
         */
        private fun charToByte(c: Char): Byte {

            return "0123456789ABCDEF".indexOf(c).toByte()
        }

    }
    private fun sendRequestWithHttpURLConnection() {
        Thread(Runnable  {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                val url = URL("https://www.baidu.com")//新建URL
                connection = url.openConnection() as HttpURLConnection//发起网络请求
                connection.setRequestMethod("POST")//请求方式
                connection.setConnectTimeout(30000)//连接最大时间
                connection.setReadTimeout(30000)//读取最大时间
                val results = connection.getInputStream()
                reader = BufferedReader(InputStreamReader(results))//写入reader
                val response = StringBuilder()
//                var line: String
//                while ((line = reader!!.readLine()) != null) {
//                    response.append(line)
//                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                if (connection != null) {
                    connection.disconnect()
                }
            }
        }).start()
    }



}