CREATE TABLE `request_event` (
  `request_event_id` char(36) NOT NULL,
  `start` bigint(20) unsigned NOT NULL,
  `end` bigint(20) unsigned NOT NULL,
  `method` tinyint(3) unsigned NOT NULL,
  `uri` varchar(250) NOT NULL,
  `query_string` varchar(250) DEFAULT NULL,
  `ip` varchar(200) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `minute` int(10) unsigned NOT NULL DEFAULT '0',
  `hour` int(10) unsigned NOT NULL DEFAULT '0',
  `day` int(10) unsigned NOT NULL DEFAULT '0',
  `year` int(10) unsigned NOT NULL DEFAULT '0',
  `month` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`request_event_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;