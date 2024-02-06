package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryNetwork;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TelemetryEndpoint {

    private int id;
    private TelemetryNetwork network;

    private PriorityQueue<TelemetryPacket> inPacketQueue;
    private PriorityQueue<TelemetryPacket> outPacketQueue;

    //private Discriminator discriminator; or
    //private AuthKey authKey;

    public TelemetryEndpoint(int id) {
        this.network = null;
        this.id = id;

        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
    }

    public void update() {
        //reads packet queues and processes in packets and sends out packets

        //read in packet queue
        if(!this.inPacketQueue.isEmpty())  {
            for(int i = 0; i < this.inPacketQueue.toArray().length; i++) {
                TelemetryPacket packetIn = this.inPacketQueue.poll();
                //TODO: PROCESSING LOGIC FOR PACKETS
            }
        }

        //read out packet queue and send packets to network
        //TODO: add specific destinations for the packets, like a single endpoint in the connection or several specific ones
        if(!this.outPacketQueue.isEmpty()) {
            for(int i = 0; i < this.outPacketQueue.toArray().length; i++) {
                this.network.sendPacketToNetwork(this.outPacketQueue.poll());
            }
        }
    }

    public void receivePacket(TelemetryPacket packet) {
        this.inPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Received packet on endpoint with id " + id);
    }

    public void sendPacket(TelemetryPacket packet) {
        if(this.network == null) {
            GCSBLog.LOGGER.error("[ERROR] Telemetry Endpoint with id " + this.getId() + " cant send packet because it has no bound network!");
            return;
        }
        this.outPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Sent packet from endpoint with id " + id);
    }

    public void setNetwork(TelemetryNetwork network) {
        this.network = network;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}