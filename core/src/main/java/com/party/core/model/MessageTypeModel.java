package com.party.core.model;

public class MessageTypeModel {
	/**
	 * 消息类型（1：赞；2：评论；3：关注；4：系统通知; 5：活动；6：商品）
	 */
	public static final String MESSAGE_TYPE_LOVE = "1";
	public static final String MESSAGE_TYPE_COMMENT = "2";
	public static final String MESSAGE_TYPE_FOCUS = "3";
	public static final String MESSAGE_TYPE_SYS = "4";
	public static final String MESSAGE_TYPE_ACT = "5";
	public static final String MESSAGE_TYPE_GOODS = "6";

	/**
	 * tag 分类 用于系统通知MESSAGE_TYPE_SYS，区分社区，圈子，活动，商品预定，会员等消息
	 */
	public static final String MESSAGE_TAG_UNDEFIND = "-1"; // 未定义
	public static final String MESSAGE_TAG_DISCOVERY = "1"; // 社区
	public static final String MESSAGE_TAG_CIRCLE = "2"; // 圈子
	public static final String MESSAGE_TAG_MEMBER = "3"; // 会员

	/**
	 * 设置消息是否已读
	 */
	public static final int MESSAGE_STATUS_NEW = 1;
	public static final int MESSAGE_STATUS_READ = 0;

	/**
	 * 消息logo url
	 */
	public static final String MSG_LOGO_ACT = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_act.jpg";
	public static final String MSG_LOGO_GOODS = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_goods.jpg";
	public static final String MSG_LOGO_DISCOVERY = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_discovery.jpg";
	public static final String MSG_LOGO_CIRCLE = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_circle.jpg";
	public static final String MSG_LOGO_MEMBER = "http://tongxingzhe-10052192.file.myqcloud.com/userfiles/message/msg_member.jpg";
}
