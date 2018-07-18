require 'json'

package = JSON.parse(File.read('../package.json'))

Pod::Spec.new do |s|
  s.name           = 'RNQuickFirebase'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.license        = package['license']
  s.author         = package['author']
  s.homepage       = package['homepage']
  s.source         = { :git => package['repository']['url'], :tag => s.version }

  s.requires_arc   = true
  s.platform       = :ios, '9.0'

  s.preserve_paths = 'LICENSE', 'README.md', 'package.json', 'index.js'
  s.source_files   = '*.{h,m}'

  s.dependency 'React'
  s.dependency 'Firebase/Core'
  s.dependency 'FirebaseAnalytics'
end