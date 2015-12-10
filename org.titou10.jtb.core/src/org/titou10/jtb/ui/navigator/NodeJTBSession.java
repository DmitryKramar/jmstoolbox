/*
 * Copyright (C) 2015 Denis Forveille titou10.titou10@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.titou10.jtb.ui.navigator;

import java.util.SortedSet;
import java.util.TreeSet;

import org.titou10.jtb.jms.model.JTBQueue;
import org.titou10.jtb.jms.model.JTBSession;
import org.titou10.jtb.jms.model.JTBTopic;

//@formatter:off
/**
 * Manages a JTBSession Node
 * 
 * Has 2 "folders": 
 * - Queues 
 * - Topics
 * 
 * @author Denis Forveille
 * 
 */
//@formatter:on
public class NodeJTBSession extends NodeAbstract {

   private SortedSet<NodeFolder<?>> folders;

   // -----------
   // Constructor
   // -----------

   public NodeJTBSession(JTBSession jtbSession) {
      super(jtbSession, null);
   }

   // -----------
   // NodeAbstract
   // -----------

   @Override
   public SortedSet<NodeFolder<?>> getChildren() {
      return folders;
   }

   @Override
   public Boolean hasChildren() {
      JTBSession jtbSession = (JTBSession) getBusinessObject();

      folders = new TreeSet<>();

      // No children if the session is not connected
      if (!(jtbSession.isConnected())) {
         return false;
      }

      SortedSet<NodeJTBQueue> nodeQueues = new TreeSet<>();
      for (JTBQueue jtbQueue : jtbSession.getJtbQueues()) {
         nodeQueues.add(new NodeJTBQueue(jtbQueue, this));
      }
      folders.add(new NodeFolder<NodeJTBQueue>("Queues", this, nodeQueues));

      SortedSet<NodeJTBTopic> nodeTopics = new TreeSet<>();
      for (JTBTopic jtbTopic : jtbSession.getJtbTopics()) {
         nodeTopics.add(new NodeJTBTopic(jtbTopic, this));
      }
      folders.add(new NodeFolder<NodeJTBTopic>("Topics", this, nodeTopics));

      return true;
   }

}
