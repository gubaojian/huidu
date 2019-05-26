package net.java.mapyou.ao;


import java.util.List;

import com.google.code.efurture.common.result.Result;

import net.java.mapyou.mybatis.domain.DeviceDO;

public interface DeviceAO {

	/**
	 * 更新设备信息，
	 * 如果设备信息已存，返回已存在的设备信息。
	 * 如果设备信息不存在，创建新的设备信息，检测一分钟创建的个个数。
	 * 返回未读消息个数
	 * */
	public Result<DeviceDO> registerDevice(DeviceDO device);
	

	/**
	 * 上报push的token信息
	 * */
	public Result<Boolean> reportPushToken(long id, String locationToken, String pushToken);
	
	
	
	/**
	 * 查询已关注信息列表
	 * */
	public Result<List<DeviceDO>> getTrackDeviceList(long deviceId, String deviceToken);
	
	
	/**
	 * 添加关注设备列表
	 * */
	public Result<Boolean> addTrackDevice(long deviceId, String deviceToken, String trackToken);
	
	
	
	/**
	 * 删除关注设备列表
	 * */
	public Result<Boolean> deleteTrackDevice(long deviceId, long trackId);
	
	
	/**
	 * 检查设备权限,如果设备存在，返回对应的设备信息
	 * */
	public Result<DeviceDO> checkDevice(long deviceId, String deviceToken);
	
	
	/**
	 * 检测设备是否跟踪
	 * */
	public Result<Boolean> checkDeviceTrack(long deviceId, long trackId);
	
	
	/**
	 * 删除关注设备列表
	 * */
	//public Result<Boolean> deleteTrackDevice(long id, String locationToken, String trackToken);
	
	
	
	
	
	
}
