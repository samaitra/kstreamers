package com.example.kstreamers

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang.SerializationException;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.marshaller.jdk.JdkMarshaller;


class CacheEventSerializer : Serializer<CacheEvent>() {
    /**
     * If true, the type this serializer will be used for is considered immutable.
     * This causes [.copy] to return the original object.
     */
    init {
        setImmutable(true)
    }

    override fun write(kryo: Kryo, output: Output, cacheEvent: CacheEvent) {
        try {
            output.write(marsh.marshal(cacheEvent))
        } catch (e: IgniteCheckedException) {
            throw SerializationException("Failed to serialize cache event!", e)
        }

    }

    override fun read(kryo: Kryo, input: Input, cacheEventClass: Class<CacheEvent>): CacheEvent {
        try {
            return marsh.unmarshal(input, CacheEvent::class.java.classLoader)
        } catch (e: IgniteCheckedException) {
            throw SerializationException("Failed to deserialize cache event!", e)
        }

    }

    companion object {

        /** Marshaller.  */
        private val marsh = JdkMarshaller()
    }
}