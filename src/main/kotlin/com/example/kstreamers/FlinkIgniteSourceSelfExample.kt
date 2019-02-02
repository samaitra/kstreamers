package com.example.kstreamers

import java.util.ArrayList
import org.apache.ignite.events.CacheEvent
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.ignite.source.flink.IgniteSource
import org.apache.ignite.Ignition
import org.apache.ignite.events.EventType


    /**
     * Validation for the Flink source with EventCount and IgnitePredicate Filter. Ignite started in source based on
     * what is specified in the configuration file.
     */
    fun main(args: Array<String>) {
        /** Cache name.  */
        val TEST_CACHE = "testCache"

        /** Grid Name.  */
        val GRID_NAME = "igniteServerNode"

        /** Ignite test configuration file.  */
        val GRID_CONF_FILE = "/Users/saikat/git/kstreamers/src/main/resources/example-ignite.xml";

        val ignite = Ignition.start(GRID_CONF_FILE)

        val cache = ignite.getOrCreateCache<Int, Int>(TEST_CACHE)

        val igniteSrc = IgniteSource(TEST_CACHE)

        igniteSrc.setIgnite(ignite)
        igniteSrc.setEvtBatchSize(1)
        igniteSrc.setEvtBufTimeout(100)

        igniteSrc.start(null, EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_ENTRY_CREATED)

        val env = StreamExecutionEnvironment.getExecutionEnvironment()
        env.config.disableSysoutLogging()
        env.config.registerTypeWithKryoSerializer(CacheEvent::class.java, CacheEventSerializer::class.java)

        val stream = env.addSource(igniteSrc)
        var cnt = 0

        val eventList = ArrayList<Int>()

        while (cnt < 10) {

            cache.put(cnt, cnt)

            eventList.add(cnt)

            cnt++

        }
        stream.print()
        env.execute()
    }