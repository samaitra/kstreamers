package com.example.kstreamers

import org.apache.ignite.Ignition
import org.apache.ignite.client.ClientException
import org.apache.ignite.configuration.ClientConfiguration

object IgniteClient {
    /** Entry point.  */
    @JvmStatic
    fun main(args: Array<String>) {
        val cfg = ClientConfiguration().setAddresses("127.0.0.1:10800")

        try {
            Ignition.startClient(cfg).use { igniteClient ->
                println()
                println(">>> Thin client put-get example started.")

                val cache = igniteClient.getOrCreateCache<Int, Int>("testCache")
                var cnt = 0

                val eventList = ArrayList<Int>()

                while (cnt < 10000000) {

                    cache.put(cnt, cnt)

                    eventList.add(cnt)

                    cnt++

                    Thread.sleep(10)

                }

            }
        } catch (e: ClientException) {
            System.err.println(e.message)
        } catch (e: Exception) {
            System.err.format("Unexpected failure: %s\n", e)
        }

    }
}