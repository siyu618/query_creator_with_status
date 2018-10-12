

CREATE TABLE `request_create_status` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `request_id` varchar(50) NOT NULL UNIQUE COMMENT 'request id',
   `request_type` varchar(50) NOT NULL COMMENT 'request type: one service mapping to one type',
   `request_status` int(11) NOT NULL COMMENT 'request status',
   `request_url` varchar(2000) NOT NULL COMMENT 'request url',
   `request_post_data` varchar(2000) COMMENT 'request post data: in string pattern',
   `detail` varchar(2000) NOT NULL COMMENT 'detail',
   `retry_times` int(11) NOT NULL COMMENT 'retry times',
   `create_time` datetime NOT NULL COMMENT 'create time',
   `last_update_time` datetime NOT NULL COMMENT 'last update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
