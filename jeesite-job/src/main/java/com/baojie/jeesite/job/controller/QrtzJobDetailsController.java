package com.baojie.jeesite.job.controller;

import com.baojie.jeesite.common.base.BaseController;
import com.baojie.jeesite.entity.job.QrtzJobDetails;
import com.baojie.jeesite.job.service.QrtzJobDetailsService;
import com.baojie.jeesite.util.http.ResponseMessage;
import com.baojie.jeesite.util.http.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：冀保杰
 * @date：2018-08-20
 * @desc：
 */
@Api(value = "/qrtzJobDetails", description = "定时任务操作接口")
@RestController
@RequestMapping("/qrtzJobDetails")
public class QrtzJobDetailsController extends BaseController<QrtzJobDetailsService, QrtzJobDetails> {

	@ApiOperation(value = "查询定时任务")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseMessage<List<QrtzJobDetails>> listByPage() {
		return Result.success( baseBiz.findMapList(null));
	}
	
	@ApiOperation(value = "添加定时任务")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseMessage<Integer> addQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails) throws Exception {
		return Result.success(baseBiz.createQrtzJobDetails(qrtzJobDetails));
	}
	
	@ApiOperation(value = "修改定时任务")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseMessage<Integer> updateQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails) throws Exception {
		return Result.success( baseBiz.updateQrtzJobDetails(qrtzJobDetails));
	}
	
	@ApiOperation(value = "删除定时任务")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseMessage<Integer> deleteQrtzJobDetails(@RequestBody QrtzJobDetails qrtzJobDetails) throws Exception{
		return Result.success(baseBiz.deleteQrtzJobDetails(qrtzJobDetails));
	}
	
	@ApiOperation(value = "暂停定时任务")
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	public ResponseMessage<Integer> pauseJob(@RequestBody QrtzJobDetails qrtzJobDetails) throws Exception{
		return Result.success(baseBiz.pauseJob(qrtzJobDetails));
	}
	
	@ApiOperation(value = "恢复定时任务")
	@RequestMapping(value = "/resume", method = RequestMethod.POST)
	public ResponseMessage<Integer> resumeJob(@RequestBody QrtzJobDetails qrtzJobDetails) throws Exception{
		return Result.success( baseBiz.resumeJob(qrtzJobDetails));
	}
	
}
