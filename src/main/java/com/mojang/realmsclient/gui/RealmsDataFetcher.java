package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsNotification;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.task.DataFetcher;
import com.mojang.realmsclient.gui.task.RepeatedDelayStrategy;
import com.mojang.realmsclient.util.RealmsPersistence;
import net.minecraft.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OnlyIn(Dist.CLIENT)
public class RealmsDataFetcher {
   public final DataFetcher dataFetcher = new DataFetcher(Util.ioPool(), TimeUnit.MILLISECONDS, Util.timeSource);
   public final DataFetcher.Task<List<RealmsNotification>> notificationsTask;
   public final DataFetcher.Task<List<RealmsServer>> serverListUpdateTask;
   public final DataFetcher.Task<Integer> pendingInvitesTask;
   public final DataFetcher.Task<Boolean> trialAvailabilityTask;
   public final DataFetcher.Task<RealmsNews> newsTask;
   public final RealmsNewsManager newsManager = new RealmsNewsManager(new RealmsPersistence());

   public RealmsDataFetcher(RealmsClient p_238853_) {
      this.serverListUpdateTask = this.dataFetcher.createTask("server list", () -> {
         return p_238853_.listWorlds().servers;
      }, Duration.ofSeconds(60L), RepeatedDelayStrategy.CONSTANT);
      this.pendingInvitesTask = this.dataFetcher.createTask("pending invite count", p_238853_::pendingInvitesCount, Duration.ofSeconds(10L), RepeatedDelayStrategy.exponentialBackoff(360));
      this.trialAvailabilityTask = this.dataFetcher.createTask("trial availablity", p_238853_::trialAvailable, Duration.ofSeconds(60L), RepeatedDelayStrategy.exponentialBackoff(60));
      this.newsTask = this.dataFetcher.createTask("unread news", p_238853_::getNews, Duration.ofMinutes(5L), RepeatedDelayStrategy.CONSTANT);
      this.notificationsTask = this.dataFetcher.createTask("notifications", p_238853_::getNotifications, Duration.ofMinutes(5L), RepeatedDelayStrategy.CONSTANT);
   }
}