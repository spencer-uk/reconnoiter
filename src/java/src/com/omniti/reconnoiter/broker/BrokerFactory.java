/*
 * Copyright (c) 2013, Circonus, Inc. All rights reserved.
 * Copyright (c) 2010, OmniTI Computer Consulting, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name OmniTI Computer Consulting, Inc. nor the names
 *       of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written
 *       permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.omniti.reconnoiter.broker;

import org.apache.activemq.broker.BrokerService;

import com.omniti.reconnoiter.StratconConfig;

public class BrokerFactory {
  private static BrokerService amqBroker = null;
  
  public static IMQBroker getBroker(StratconConfig config) {
    // TODO if the broker is null default the AMQAdapter
    String broker = config.getBroker();
    
    if (broker == null)
      return new AMQBroker(config);
    
    if (broker.compareToIgnoreCase("rabbitmq") == 0) {
      return new RabbitBroker(config);
    }
    else if (broker.compareToIgnoreCase("activemq") == 0) {
      return new AMQBroker(config);
    }
    return new AMQBroker(config);
  }
  

  public static BrokerService getAMQBrokerService() {
    if(amqBroker == null) System.err.println("Cannot broker messages");
    return amqBroker;
  }
  public static BrokerService getAMQBrokerService(String stomp) {
    if(amqBroker != null) return amqBroker;
    try {
      amqBroker = new BrokerService();
      amqBroker.setUseJmx(false);
      amqBroker.addConnector("vm://localhost");
      amqBroker.addConnector(stomp);
      amqBroker.start();
    } catch(Exception e) {
      System.err.println("Cannot broker messages: " + e);
    }
    return amqBroker;
  }

}
