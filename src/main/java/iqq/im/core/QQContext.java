 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCore
 * Package  : iqq.im
 * File     : QQContext.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.core;

import iqq.im.actor.QQActor;
import iqq.im.bean.QQAccount;
import iqq.im.event.QQNotifyEvent;

/**
 *
 * QQ环境上下文，所有的模块都是用QQContext来获取对象
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public interface QQContext {
	public void pushActor(QQActor actor);
	public void fireNotify(QQNotifyEvent event);
	public <T extends QQModule> T getModule(QQModule.Type type);
	public <T extends QQService> T getSerivce(QQService.Type type);
	public QQAccount getAccount();
	public QQSession getSession();
	public QQStore   getStore();
}
