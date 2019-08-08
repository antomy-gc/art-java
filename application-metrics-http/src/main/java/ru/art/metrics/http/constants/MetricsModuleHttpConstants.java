/*
 *    Copyright 2019 ART
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.art.metrics.http.constants;

import org.apache.http.entity.ContentType;
import ru.art.http.constants.MimeToContentTypeMapper;
import ru.art.http.mime.MimeType;
import static io.prometheus.client.exporter.common.TextFormat.CONTENT_TYPE_004;

public interface MetricsModuleHttpConstants {
    MimeToContentTypeMapper METRICS_CONTENT_TYPE = new MimeToContentTypeMapper(MimeType.valueOf(CONTENT_TYPE_004), ContentType.parse(CONTENT_TYPE_004));
}