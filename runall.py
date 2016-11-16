#!/usr/bin/env python3

import subprocess
import json
import os
import shutil
import sys

TIMEOUT = 10 * 60

if os.path.exists('log'):
    shutil.rmtree('log')
os.mkdir('log')

cmd_prefix = ['java', '-jar', 'target' + os.sep + 'codisexp-1.0-jar-with-dependencies.jar']

# subprocess.check_output(cmd_prefix + ['-l']).decode("utf-8")
with open(sys.argv[1]) as data_file:    
    configs = json.load(data_file)

for algorithm in configs['algorithms']:
    for subject in configs['subjects']:
        for tests in subject['tests']:
            for components in subject['components']:
                if subject['name'] == 'icfp':
                    cmd_suffix = ['-s', subject['name'], '-t', tests, '-a', algorithm, '-c', components, '-b']
                else:    
                    cmd_suffix = ['-s', subject['name'], '-t', tests, '-a', algorithm, '-c', components]
                try:
                    proc = subprocess.Popen(cmd_prefix + cmd_suffix)
                    code = proc.wait(timeout=TIMEOUT)                
                except subprocess.TimeoutExpired:
                    print('Timeout. Killing process...')                
                    proc.kill()
                if os.path.isfile('experiments.log'):
                    logfile = 'log/{}-{}-{}-{}.log'.format(algorithm, subject['name'], tests, components)
                    shutil.move('experiments.log', logfile)

